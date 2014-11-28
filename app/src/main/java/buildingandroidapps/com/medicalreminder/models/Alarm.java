package buildingandroidapps.com.medicalreminder.models;

import java.util.Date;

/**
 * Created by Drasko on 21.11.2014..
 */
public class Alarm {
    Medicine medicine;
    Date startDate;
    Date endDate;
    int numberOfTaking;
    long interval;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfTaking() {
        return numberOfTaking;
    }

    public void setNumberOfTaking(int numberOfTaking) {
        this.numberOfTaking = numberOfTaking;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
