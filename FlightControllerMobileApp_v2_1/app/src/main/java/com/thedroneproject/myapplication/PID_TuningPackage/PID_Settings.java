package com.thedroneproject.myapplication.PID_TuningPackage;

/*
    Class with current PID setting for all controllers with feature to set values of the proper one
 */

import java.util.ArrayList;
import java.util.List;

public class PID_Settings {
    // singleton
    private static PID_Settings instance = new PID_Settings();

    public static PID_Settings getInstance() {
        return instance;
    }

    // remaining content
    private List<PID_Bundle> controllersList;
    private PID_Bundle activeController = null;
    private int pidSpinnerStep = 10;


    // Receive List with controllers names and create array of PID_Bundle type
    // Controller ID is position in this array
    public void createControllersFromNames(String[] namesList)
    {
        if (namesList.length == 0)
            return;

        controllersList = new ArrayList<>();

        for (int i=0; i<namesList.length; i++)
        {
            PID_Bundle newBundle = new PID_Bundle();
            newBundle.setID(i);
            controllersList.add(newBundle);
        }

        setActiveController(0);
    }


    // Selects which PID controller from list will be set by PID values setter methods
    public boolean setActiveController(int ID)
    {
        if (ID >= controllersList.size())
            return false;

        activeController = controllersList.get(ID);
        return true;
    }

    // Returns the active controller
    public PID_Bundle getActiveController()
    {
        return activeController;
    }

    public void setPidSpinnerStep(int step)
    {
        this.pidSpinnerStep = step;
    }

    public int getPidSpinnerStep()
    {
        return this.pidSpinnerStep;
    }
}
