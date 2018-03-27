package com.yang.reciver.model;

public class Project {
    private String finishTime;
    private String drainageRangeName;
    private String wlPoint;
    private String invEstimate;
    private String memo;
    private String respOrganization;
    private int id;
    private String measurement;

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getDrainageRangeName() {
        return drainageRangeName;
    }

    public void setDrainageRangeName(String drainageRangeName) {
        this.drainageRangeName = drainageRangeName;
    }

    public String getWlPoint() {
        return wlPoint;
    }

    public void setWlPoint(String wlPoint) {
        this.wlPoint = wlPoint;
    }

    public String getInvEstimate() {
        return invEstimate;
    }

    public void setInvEstimate(String invEstimate) {
        this.invEstimate = invEstimate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRespOrganization() {
        return respOrganization;
    }

    public void setRespOrganization(String respOrganization) {
        this.respOrganization = respOrganization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
