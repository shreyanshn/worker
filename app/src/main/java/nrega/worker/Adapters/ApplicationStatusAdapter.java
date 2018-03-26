package nrega.worker.Adapters;

import android.support.v7.widget.RecyclerView;
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

        public MyViewHolder(View view){
            super(view);
            name=(TextView)view.findViewById(R.id.astatus_name);
            worklocation=(TextView)view.findViewById(R.id.astatus_worklocation);
            status=(TextView)view.findViewById(R.id.astatus);
            startdate=(TextView)view.findViewById(R.id.astatus_startdate);
            endate=(TextView)view.findViewById(R.id.astatus_enddate);
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
    }

    @Override
    public int getItemCount() {
        return applicationStatusList.size();
    }

}
