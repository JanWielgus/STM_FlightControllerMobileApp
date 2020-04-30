package com.thedroneproject.myapplication.PID_TuningPackage;

public class PID_Bundle
{
    private float kP;
    private float kI;
    private float kD;
    private int iMax;

    public PID_Bundle()
    {
        kP = 0;
        kI = 0;
        kD = 0;
        iMax = 0;
    }


    // Getters

    public float getkP() {
        return kP;
    }

    public float getkI() {
        return kI;
    }

    public float getkD() {
        return kD;
    }

    public int getiMax() {
        return iMax;
    }


    // Setters

    public void setkP(float kP) {
        this.kP = kP;
    }

    public void setkI(float kI) {
        this.kI = kI;
    }

    public void setkD(float kD) {
        this.kD = kD;
    }

    public void setiMax(int iMax) {
        this.iMax = iMax;
    }

    public void set(float kP, float kI, float kD, int iMax)
    {
        setkP(kP);
        setkI(kI);
        setkD(kD);
        setiMax(iMax);
    }
}
