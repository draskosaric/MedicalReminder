package buildingandroidapps.com.medicalreminder.models;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Drasko on 27.11.2014..
 */
public class AlarmTime implements Comparable<AlarmTime>{

    private Alarm alarm;
    private Date momentOfAlarm;

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Date getMomentOfAlarm() {
        return momentOfAlarm;
    }

    public void setMomentOfAlarm(Date momentOfAlarm) {
        this.momentOfAlarm = momentOfAlarm;
    }


    @Override
    public int compareTo(AlarmTime another) {
        return AlarmTimeDateComparator.compare(this, another);
    }

    public static Comparator<AlarmTime> AlarmTimeDateComparator  = new Comparator<AlarmTime>() {
        @Override
        public int compare(AlarmTime lhs, AlarmTime rhs) {
            return lhs.momentOfAlarm.compareTo(rhs.momentOfAlarm);
        }
    };
}
