package buildingandroidapps.com.medicalreminder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import buildingandroidapps.com.medicalreminder.enums.MedicineTypes;
import buildingandroidapps.com.medicalreminder.models.Alarm;
import buildingandroidapps.com.medicalreminder.models.AlarmTime;
import buildingandroidapps.com.medicalreminder.models.Medicine;


public class MainActivity extends ActionBarActivity {

    private TextView txtNumberOfAlarms;
    private ArrayList<AlarmTime> alarmTimes;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        calculateSomeSimulationData();
        showData();

    }

    private void initViews() {
        txtNumberOfAlarms = (TextView) findViewById(R.id.number_of_alarms);
        txtNumberOfAlarms.setText(getString(R.string.none));
    }

    private void showData() {
        ListView listView = (ListView) findViewById(R.id.list_of_alarms);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        updateTxtNumberOfAlarms();
    }

    private void updateTxtNumberOfAlarms() {
        String txtToBeShown = alarmTimes == null || alarmTimes.size() == 0 ? getString(R.string.none) : String.valueOf(alarmTimes.size());
        txtNumberOfAlarms.setText(txtToBeShown);
    }

    private void calculateSomeSimulationData() {
        alarmTimes = new ArrayList<AlarmTime>();
        for (int i = 0; i < 2; i++) {
            Alarm alarm = getAlarm(i);
            alarmTimes.addAll(getAlarmTimes(alarm));
        }
        Collections.sort(alarmTimes);
    }

    private Alarm getAlarm(int i) {
        Alarm alarm = new Alarm();
        Medicine medicine = new Medicine();
        switch (i) {
            case 0:
                medicine.setName("Brufen");
                medicine.setDosage("400mg");
                medicine.setMedicineType(MedicineTypes.PILL);
                alarm.setInterval(8*60); // take after 8 hours
                alarm.setNumberOfTaking(10);
                break;
            case 1:
                medicine.setName("Robitussin");
                medicine.setDosage("10mL");
                medicine.setMedicineType(MedicineTypes.SYRUP);
                alarm.setInterval(5*60); // take after 5 hours
                alarm.setNumberOfTaking(20);
                break;
            default:
                break;
        }
        alarm.setMedicine(medicine);
        alarm.setStartDate(new Date());
        return alarm;
    }

    private ArrayList<AlarmTime> getAlarmTimes(Alarm alarm) {
        final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
        ArrayList<AlarmTime> times = new ArrayList<AlarmTime>();

        for (int i = 0; i < alarm.getNumberOfTaking(); i++) {
            AlarmTime time = new AlarmTime();
            time.setAlarm(alarm);
            //on starting date we are adding number of minutes to get the time in future when we expect next alarm
            time.setMomentOfAlarm(new Date( alarm.getStartDate().getTime() + (i+1)*alarm.getInterval()*ONE_MINUTE_IN_MILLIS ));
            times.add(time);
        }
        return times;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return alarmTimes.size();
        }

        @Override
        public Object getItem(int position) {
            return alarmTimes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            // if row is not created yet, now is the time
            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.row_alarms, parent, false);
            }

            AlarmTime currentAlarmTime = alarmTimes.get(position);

            // Locate ImageView
            // !!!! IMPORTANT !!!!
            // Don't call simple findViewById method (which actually asks activity to locate view in its layout),
            // but call row's findViewById to locate view in its layout
            ImageView medicineTypeIcon = (ImageView) row.findViewById(R.id.imageMedicineType);

            // Standard way for retrieving images if its identifier is dynamically determined and setting it
            // (You could create some additional method that will, based on medicine type, return appropriate image identifier
            // I used this just to play around; operations on String are expensive, so approach I proposed in line before is totally OK)
            String imageUri = "@drawable/med_types_" + String.valueOf(currentAlarmTime.getAlarm().getMedicine().getMedicineType().getValue());
            int imageResource = getResources().getIdentifier(imageUri, null, getPackageName());
            medicineTypeIcon.setImageResource(imageResource);

            // We don't have to create new variable if we want just to set text to TextView,
            // so next three lines set text to three different TextViews
            ((TextView) row.findViewById(R.id.txtMedicineName)).setText(currentAlarmTime.getAlarm().getMedicine().getName());
            ((TextView) row.findViewById(R.id.txtDosage)).setText(currentAlarmTime.getAlarm().getMedicine().getDosage());
            ((TextView) row.findViewById(R.id.txtNextTimeOfAlarm)).setText(currentAlarmTime.getMomentOfAlarm().toString());

            return row;
        }
    }

}
