package com.yang.reciver.model;

import com.yang.reciver.annotation.CName;

public class Project {
    @CName("序号")
    private String id;
    @CName("内涝点")
    private String wlPoint;
    @CName("责任单位")
    private String respOrganization;
    @CName("流域名称")
    private String drainageRangeName;
    @CName("投资估算")
    private String invEstimate;
    @CName("完成时间")
    private String finishTime;
    @CName("备注")
    private String memo;


    @CName("治理措施")
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

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
