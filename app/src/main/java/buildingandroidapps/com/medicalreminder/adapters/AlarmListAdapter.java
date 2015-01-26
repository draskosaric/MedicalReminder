package buildingandroidapps.com.medicalreminder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import buildingandroidapps.com.medicalreminder.R;
import buildingandroidapps.com.medicalreminder.models.AlarmTime;


public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmsListViewHolder> {

    private ArrayList<AlarmTime> alarmTimes;
    private Context context;

    public AlarmListAdapter(ArrayList<AlarmTime> alarmTimes, Context context) {
        this.alarmTimes = alarmTimes;
        this.context = context;
    }

    @Override
    public AlarmsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_alarms, parent, false);
        return new AlarmsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlarmsListViewHolder holder, final int position) {
        final AlarmTime alarmTime = alarmTimes.get(position);
        String imageUri = "@drawable/med_types_" + String.valueOf(alarmTime.getAlarm().getMedicine().getMedicineType().getValue());
        int imageResource = context.getResources().getIdentifier(imageUri, null, context.getPackageName());
        holder.imageMedicineType.setImageResource(imageResource);
        holder.txtMedicineName.setText(alarmTime.getAlarm().getMedicine().getName());
        holder.txtDosage.setText(alarmTime.getAlarm().getMedicine().getDosage());
        holder.txtNextTimeOfAlarm.setText(alarmTime.getMomentOfAlarm().toString());
    }

    @Override
    public int getItemCount() {
        return alarmTimes.size();
    }

    class AlarmsListViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageMedicineType;
        public TextView txtMedicineName;
        public TextView txtDosage;
        public TextView txtNextTimeOfAlarm;

        public AlarmsListViewHolder(View itemView) {
            super(itemView);
            imageMedicineType = (ImageView) itemView.findViewById(R.id.imageMedicineType);
            txtMedicineName = (TextView) itemView.findViewById(R.id.txtMedicineName);
            txtDosage = (TextView) itemView.findViewById(R.id.txtDosage);
            txtNextTimeOfAlarm = (TextView) itemView.findViewById(R.id.txtNextTimeOfAlarm);
        }
    }

    public void addAlarmTime( AlarmTime alarmTime ) {
        boolean isPositionFound = false;
        int position = 0;
        while (!isPositionFound && position < alarmTimes.size()) {
            if (alarmTimes.get(position).compareTo(alarmTime) > 0) {
                isPositionFound = true;
            } else {
                position++;
            }
        }
        alarmTimes.add(position, alarmTime);
        notifyItemInserted(position);
    }

}
