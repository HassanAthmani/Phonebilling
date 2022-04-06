package phonebilling;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.*;

public class Phonebilling {

    long endTime, startTime;	//call start and end times
    SimpleDateFormat sdf = new SimpleDateFormat(); //An object to format time
    int otherNet;

    /**
     * Method to calculate the total charge on a call made by a customer
     *
     * @paramcallTime
     * @return totalCharge
     */
    public double billCall(double callTime) {
        sdf.applyPattern("HH");
        int time = Integer.valueOf(sdf.format(System.currentTimeMillis()));//The time of day (24-hr clock system when the call was made
        int chargePerMin;	//The charge rate

        //check the time to determine the charge rate
        //	whether it's 6AM-5:59PM or 6PM-5:59AM
        if (time > 6 && time <= 18) {
            chargePerMin = 4;
        } else if (otherNet == 1) {
            chargePerMin=5;
        } else {
            chargePerMin = 3;
        }

        //calculate the total charge
        double totalCharge = chargePerMin * callTime;

        //add the VAT if call takes longer than one minute
        if (callTime > 2) {
            totalCharge = totalCharge + (chargePerMin * 0.16);
        }
        else if(otherNet==1){
            totalCharge=5;
        }
        return totalCharge;
    }

    /**
     * calculates the Total time taken in the call.
     *
     * @return
     */
    public double makeCall() {
        long startTime = 0, endTime = 0;
        double totalTime;
        startTime = startCall();

        if (startTime != 0) //If call has started
        {
            endTime = endCall();
        }

        sdf.applyPattern("HH:mm:ss");
        System.out.println("The call started at " + sdf.format(startTime) + " and ended at " + sdf.format(endTime));

        totalTime = endTime - startTime;

        //divide the totalTime by 1000*60 to convert from milliseconds to seconds
        totalTime = totalTime / (1000);
//		System.out.println("The call took "+totalTime+" minutes.");
        return totalTime;
    }

    /*
	 * Method to provide a user interface to start the call and get the time when the call begun
	 * @return startTime
     */
    public long startCall() {
        int otherline = JOptionPane.showConfirmDialog(null, "Do you want to make a call to other network?"+"\n"+" Choose Yes to confirm and No for normal calls", null, 0);
        if (otherline == JOptionPane.YES_OPTION) {

            int callStart = JOptionPane.showConfirmDialog(null, "Do you want to make a call to other line?", null, 0);

            if (callStart == JOptionPane.YES_OPTION) {
                System.out.println("Call Started");

                startTime = System.currentTimeMillis();
                JOptionPane.showMessageDialog(null, "calling...");
                otherNet = 1;

            }
        } else {
            int callStart = JOptionPane.showConfirmDialog(null, "Do you want to make a normal call?", null, 0);
            if (callStart == JOptionPane.YES_OPTION) {
                System.out.println("Call Started");

                startTime = System.currentTimeMillis();
                JOptionPane.showMessageDialog(null, "calling...");

            }

        }
        
        

        return startTime;
    }

    /*
	 * Method to provide a user interface to end the call and get the time when the call ends
	 * @return endTime
     */
    public long endCall() {
        int callEnd = JOptionPane.showConfirmDialog(null, "Call is still on, do you want to end this call?", null, JOptionPane.OK_OPTION);
        if (callEnd == JOptionPane.YES_OPTION) {
            System.out.println("Call Ended");
            endTime = System.currentTimeMillis();
        } else {
            endCall();
        }
        return endTime;
    }

    public static void main(String[] args) {
        Phonebilling call = new Phonebilling();
        double callTime = call.makeCall();
        int minutes = Double.valueOf(callTime / 60).intValue();
        int seconds = Double.valueOf(callTime % 60).intValue();
        String msg = String.format("The call took %02d:%02d and was charged Ksh.%.2f", minutes, seconds, call.billCall(callTime / 60));
        String.format(msg, callTime);
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg);
        JOptionPane.showMessageDialog(null, "Thanks for choosing our services :");
    }

}
