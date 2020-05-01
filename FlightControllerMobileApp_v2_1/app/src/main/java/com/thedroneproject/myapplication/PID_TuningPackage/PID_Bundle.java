package com.thedroneproject.myapplication.PID_TuningPackage;

public class PID_Bundle
{
    private float kP;
    private float kI;
    private float kD;
    private int iMax;

    private boolean kP_ZeroFlag = false;
    private boolean kI_ZeroFlag = false;
    private boolean kD_ZeroFlag = false;
    private boolean iMax_ZeroFlag = false;

    private int ID;

    public PID_Bundle()
    {
        kP = 0;
        kI = 0;
        kD = 0;
        iMax = 0;
    }


    // Getters

    public float getkP() {
        return kP_ZeroFlag ? 0 : kP;
    }

    public float getkI() {
        return kI_ZeroFlag ? 0 : kI;
    }

    public float getkD() {
        return kD_ZeroFlag ? 0 : kD;
    }

    public int getiMax() {
        return iMax_ZeroFlag ? 0 : iMax;
    }

    public int getID() { return ID; }


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

    public void setID(int ID) { this.ID = ID; }



    public boolean iskP_ZeroFlag() {
        return kP_ZeroFlag;
    }

    public void setkP_ZeroFlag(boolean kP_ZeroFlag) {
        this.kP_ZeroFlag = kP_ZeroFlag;
    }

    public boolean iskI_ZeroFlag() {
        return kI_ZeroFlag;
    }

    public void setkI_ZeroFlag(boolean kI_ZeroFlag) {
        this.kI_ZeroFlag = kI_ZeroFlag;
    }

    public boolean iskD_ZeroFlag() {
        return kD_ZeroFlag;
    }

    public void setkD_ZeroFlag(boolean kD_ZeroFlag) {
        this.kD_ZeroFlag = kD_ZeroFlag;
    }

    public boolean isiMax_ZeroFlag() {
        return iMax_ZeroFlag;
    }

    public void setiMax_ZeroFlag(boolean iMax_ZeroFlag) {
        this.iMax_ZeroFlag = iMax_ZeroFlag;
    }
}
