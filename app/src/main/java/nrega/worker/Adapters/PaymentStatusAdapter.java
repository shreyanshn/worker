package nrega.worker.Adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import nrega.worker.Model.PaymentStatus;
import nrega.worker.R;


public class PaymentStatusAdapter extends RecyclerView.Adapter<PaymentStatusAdapter.MyViewHolder> {

    private List<PaymentStatus> paymentStatusList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,paystatus,startdate,endate;

        public MyViewHolder(View view){
            super(view);
            name=(TextView)view.findViewById(R.id.paystatus_name);
            paystatus=(TextView)view.findViewById(R.id.paystatus);
            startdate=(TextView)view.findViewById(R.id.paystatus_startdate);
            endate=(TextView)view.findViewById(R.id.paystatus_enddate);
        }
    }
    public PaymentStatusAdapter (List<PaymentStatus> paymentStatusList)
    {
        this.paymentStatusList=paymentStatusList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType!=-1) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_status, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaymentStatus paymentStatus=paymentStatusList.get(position);
        holder.name.setText(paymentStatus.getName());
        holder.startdate.setText((CharSequence) paymentStatus.getStartdate());
        holder.endate.setText((CharSequence) paymentStatus.getEnddate());
        holder.paystatus.setText(paymentStatus.getPaystatus());
    }

    @Override
    public int getItemCount() {
        return paymentStatusList.size();
    }

}


