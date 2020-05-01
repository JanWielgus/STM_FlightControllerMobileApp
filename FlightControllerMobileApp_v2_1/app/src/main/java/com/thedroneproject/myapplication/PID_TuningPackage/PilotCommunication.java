package com.thedroneproject.myapplication.PID_TuningPackage;

import android.os.Handler;
import android.util.Log;

public class PilotCommunication
{
    // singleton
    private static PilotCommunication pilotCommunicationInstance = new PilotCommunication();
    public static PilotCommunication getInstance() { return pilotCommunicationInstance; }


    // other stuff

    private Handler handler;
    private static final int commUpdatePeriod = 300; // in ms, time between checking if need to send and performing receive action
    private boolean autoSending = false;
    private PID_Settings pidSettingsInstance;


    private PilotCommunication()
    {
        handler = new Handler();
        pidSettingsInstance = PID_Settings.getInstance();

        commUpdateRunnable.run();
    }


    public void send()
    {
        if (pidSettingsInstance.isNeedToSendFlagChecked())
        {
            // perform sending
            // TODO
        }
        Log.d("Dzien dobry, tu ja.", "commUpdateRunnable method has been called");
    }


    // true for auto sending enabled
    public void setAutoSending(boolean autoSendingState)
    {
        autoSending = autoSendingState;
    }



    private Runnable commUpdateRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            // receive data
            // TODO


            // send if auto sending is enabled
            if (autoSending)
                send();

            // plan next call
            handler.postDelayed(this, commUpdatePeriod);
        }
    };


}
