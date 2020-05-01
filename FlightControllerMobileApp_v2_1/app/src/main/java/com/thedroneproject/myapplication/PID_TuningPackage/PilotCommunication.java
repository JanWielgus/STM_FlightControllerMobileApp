package com.thedroneproject.myapplication.PID_TuningPackage;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PilotCommunication
{
    // singleton
    private static PilotCommunication pilotCommunicationInstance = new PilotCommunication();
    public static PilotCommunication getInstance() { return pilotCommunicationInstance; }


    // other stuff
    private boolean autoSending = false;
    private boolean oneTimeSendRequest = false;
    private PID_Settings pidSettingsInstance;
    private Activity pidTuningActivity = null;

    // communication stuff
    private DatagramSocket datagramSocket;
    private int port = 8888;
    private final int MaxBufferLen = 100;
    byte[] receiveData = new byte[MaxBufferLen];
    byte[] dataToSend = new byte[MaxBufferLen];
    DatagramPacket DpReceive = null;
    DatagramPacket DpToSend = null;
    InetAddress ipAddr;


    private PilotCommunication()
    {
        pidSettingsInstance = PID_Settings.getInstance();
    }


    public void setActivity(Activity pidTuningActivity)
    {
        this.pidTuningActivity = pidTuningActivity;
    }


    public void initializeCommunication()
    {
        if (datagramSocket == null)
            (new ConnectDatagramSocket()).execute();
    }


    // Cannot be public, because is called on separate thread
    private Runnable commSendingUpdateRunnable = new Runnable()
    {
        private byte[] temp;

        @Override
        public void run()
        {
            while (true)
            {
                // if requested sending once
                if (oneTimeSendRequest)
                {
                    privSend();

                    // reset one time sending flag
                    oneTimeSendRequest = false;
                }

                // if auto sending and need to send
                else if (autoSending && pidSettingsInstance.isNeedToSendFlagChecked())
                    privSend();


                // Slow down this thread (prevent from catching several needToSend flags
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        private void privSend()
        {
            // perform sending

            // put proper PID tuning data to dataToSend array
            int packedDataSize = packPID_dp();

            // create data packet and send it
            DpToSend = new DatagramPacket(dataToSend, packedDataSize, // !!! size at most MaxBufferLen
                    ipAddr, port);

            try {
                datagramSocket.send(DpToSend);
                pidSettingsInstance.sendingPerformed(); // set needToSend flag to false
                Log.d("Sending successful", DpToSend.getAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Sending failed: ", e.getMessage());
            }
        }


        private int packPID_dp()
        {
            dataToSend[0] = 0; // packet ID
            PID_Bundle currentPID = pidSettingsInstance.getActiveController();
            dataToSend[1] = (byte)currentPID.getID(); // controller ID

            // P
            temp = intToByteArray((int)(currentPID.getkP() * 100.f));
            for (int i=0; i<4; i++)
                dataToSend[i+2] = temp[i];
            // I
            temp = intToByteArray((int)(currentPID.getkP() * 100.f));
            for (int i=0; i<4; i++)
                dataToSend[i+6] = temp[i];
            // D
            temp = intToByteArray((int)(currentPID.getkP() * 100.f));
            for (int i=0; i<4; i++)
                dataToSend[i+10] = temp[i];
            // iMax
            temp = intToByteArray((int)currentPID.getkP());
            for (int i=0; i<4; i++)
                dataToSend[i+14] = temp[i];

            return 18; // !!! return size of packed data
        }
    };


    // true for auto sending enabled
    public void setAutoSending(boolean autoSendingState)
    {
        autoSending = autoSendingState;
    }


    public void requestOneTimeSending()
    {
        oneTimeSendRequest = true;
    }



    private Runnable commReceivingUpdateRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            while (true)
            {
                // receive data
                DpReceive = new DatagramPacket(receiveData, receiveData.length);

                try {
                    Log.e("Receive: ", "start");
                    datagramSocket.receive(DpReceive);
                    Log.e("Receive: ", "end");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Receiving failed", e.getMessage());
                }

                // TODO Handle received data
                Log.e("Received size: ", String.valueOf(DpReceive.getLength()));

                // create new buffer for next data
                // TO DO that will not be necessary if there will be known-size data packets
                //receiveData = new byte[MaxReceiveBufferLen];
            }
        }
    };



    private void showToast(String textToShow)
    {
        if (pidTuningActivity != null)
            Toast.makeText(pidTuningActivity, textToShow, Toast.LENGTH_LONG).show();
    }


    private class ConnectDatagramSocket extends AsyncTask<Void, Void, Void>
    {
        private boolean success = true;

        @Override
        protected Void doInBackground(Void... voids) {

            // Create new DatagramSocket
            try {
                datagramSocket = new DatagramSocket(null);
                datagramSocket.setReuseAddress(true);
                datagramSocket.setBroadcast(true);
                datagramSocket.bind(new InetSocketAddress(port));

            } catch (SocketException e) {
                e.printStackTrace();
                Log.e("Comm failure", e.getMessage());
                success = false;
            }


            try {
                ipAddr = InetAddress.getByAddress(new byte[]{(byte)192, (byte)168, 1, 24});
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            /*
            // Get local IP address
            try {
                ipAddr = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                Log.e("Local address failure: ", e.getMessage());
                success = false;
            }*/


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.e("koniec", "koniec");

            if (success)
            {
                showToast("Connected");

                // Start communication threads
                new Thread(commSendingUpdateRunnable).start();
                new Thread(commReceivingUpdateRunnable).start();
            }
            else
            {
                showToast("Failed to connect");
                if (pidTuningActivity != null)
                    pidTuningActivity.finish();
            }
        }
    }



    // other methods
    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >> 24), (byte)(value >> 16), (byte)(value >> 8), (byte)(value) };
    }


    private int byteArrayToInt(byte[] arr) {
        int result = arr[0] << 24 | (arr[1] & 0xFF) << 16 | (arr[2] & 0xFF) << 8 | (arr[3] & 0xFF);
        return result;
    }
}
