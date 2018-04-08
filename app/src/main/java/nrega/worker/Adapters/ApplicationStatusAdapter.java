package nrega.worker.Adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nrega.worker.Model.ApplicationStatus;
import nrega.worker.R;

public class ApplicationStatusAdapter extends RecyclerView.Adapter<ApplicationStatusAdapter.MyViewHolder> {

    private List<ApplicationStatus> applicationStatusList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,worklocation,status,startdate,endate;
        CardView cv;

        public MyViewHolder(View view){
            super(view);
            name=(TextView)view.findViewById(R.id.astatus_name);
            worklocation=(TextView)view.findViewById(R.id.astatus_worklocation);
            status=(TextView)view.findViewById(R.id.astatus);
            startdate=(TextView)view.findViewById(R.id.astatus_startdate);
            endate=(TextView)view.findViewById(R.id.astatus_enddate);
            cv = (CardView) view.findViewById(R.id.application_status_cardView);
        }
    }
    public ApplicationStatusAdapter(List<ApplicationStatus> applicationStatusList)
    {
        this.applicationStatusList=applicationStatusList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType!=-1) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.application_status, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApplicationStatus applicationStatus=applicationStatusList.get(position);
        holder.name.setText(applicationStatus.getName());
        holder.worklocation.setText(applicationStatus.getWorklocation());
        holder.startdate.setText((CharSequence) applicationStatus.getStartdate());
        holder.endate.setText((CharSequence) applicationStatus.getEnddate());
        holder.status.setText(applicationStatus.getStatus());

        Log.d("status",applicationStatus.getStatus());
        if(applicationStatus.getStatus().equals("alloted"))
            holder.status.setTextColor(Color.parseColor("#58EF61"));
        else if (applicationStatus.getStatus().equals("pending"))
            holder.status.setTextColor(Color.parseColor("#CC0000"));


    }

    @Override
    public int getItemCount() {
        return applicationStatusList.size();
    }

}
