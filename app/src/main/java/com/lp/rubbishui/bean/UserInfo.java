package com.lp.rubbishui.bean;
//{"createTime":1602393582000,"updateTime":1602393582000,"createUser":"admin","updateUser":"admin","id":23
// ,"residentName":"王洋洋","sex":0,"isPermanent":1,"idCard":"330382199402070543"
// ,"card":"330111115291151686786030940207","compType":"jm","areaCode":"330111115291","areaName":"大山村委会"
// ,"mobile":"15168678603","cradType":"masses","isClean":1,"point":"0","source":"wision"}
public class UserInfo {
    private String residentName;
    private String areaCode;
    private String areaName;
    private String mobile;

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
