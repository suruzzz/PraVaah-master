package kwa.pravaah.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class message {



    @SerializedName("TM")
    @Expose
    private String tm;
    @SerializedName("ERR")
    @Expose
    private String err;
    @SerializedName("POWER")
    @Expose
    private String power;
    @SerializedName("MODE")
    @Expose
    private String mode;
    @SerializedName("PUMP")
    @Expose
    private String pump;
    @SerializedName("EVENTHRS")
    @Expose
    private String eventHrs;
    @SerializedName("AUTO")
    @Expose
    private String auto;
    @SerializedName("SENDER")
    @Expose
    private String sender;

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPump() {
        return pump;
    }

    public void setPump(String pump) {
        this.pump = pump;
    }

    public String getEventHrs() {
        return eventHrs;
    }

    public void setEventHrs(String eventHrs) {
        this.eventHrs = eventHrs;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
