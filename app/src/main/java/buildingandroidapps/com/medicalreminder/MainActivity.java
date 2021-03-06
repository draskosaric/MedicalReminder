package buildingandroidapps.com.medicalreminder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import buildingandroidapps.com.medicalreminder.adapters.AlarmListAdapter;
import buildingandroidapps.com.medicalreminder.enums.MedicineTypes;
import buildingandroidapps.com.medicalreminder.models.Alarm;
import buildingandroidapps.com.medicalreminder.models.AlarmTime;
import buildingandroidapps.com.medicalreminder.models.Medicine;


public class MainActivity extends Activity {

    private TextView txtNumberOfAlarms;
    private ArrayList<AlarmTime> alarmTimes;
    private CustomAdapter customAdapter;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private AlarmListAdapter adapter;

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
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addButton);
        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AlarmTime alarmTime : getAlarmTimes(getAlarm(1)) ) {
                    adapter.addAlarmTime(alarmTime);
                }
            }
        });
    }

    private void showData() {
        recyclerView = (RecyclerView) findViewById(R.id.list_of_alarms);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlarmListAdapter(alarmTimes, this);
        recyclerView.setAdapter(adapter);
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
        sortAlarms();
    }

    private void sortAlarms() {
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

    private class ViewHolder {
        public ImageView imageMedicineType;
        public TextView txtMedicineName;
        public TextView txtDosage;
        public TextView txtNextTimeOfAlarm;

        public ViewHolder(View row) {
            imageMedicineType = (ImageView) row.findViewById(R.id.imageMedicineType);
            txtMedicineName = (TextView) row.findViewById(R.id.txtMedicineName);
            txtDosage = (TextView) row.findViewById(R.id.txtDosage);
            txtNextTimeOfAlarm = (TextView) row.findViewById(R.id.txtNextTimeOfAlarm);
        }

        public void bind(AlarmTime alarmTime) {
            String imageUri = "@drawable/med_types_" + String.valueOf(alarmTime.getAlarm().getMedicine().getMedicineType().getValue());
            int imageResource = getResources().getIdentifier(imageUri, null, getPackageName());
            imageMedicineType.setImageResource(imageResource);
            txtMedicineName.setText(alarmTime.getAlarm().getMedicine().getName());
            txtDosage.setText(alarmTime.getAlarm().getMedicine().getDosage());
            txtNextTimeOfAlarm.setText(alarmTime.getMomentOfAlarm().toString());
        }
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
            ViewHolder viewHolder;

            // if row is not created yet, now is the time
            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.row_alarms, parent, false);
            }

            if (row.getTag() == null) {
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) row.getTag();
            }

            AlarmTime currentAlarmTime = alarmTimes.get(position);
            viewHolder.bind(currentAlarmTime);
            return row;
        }
    }

}
