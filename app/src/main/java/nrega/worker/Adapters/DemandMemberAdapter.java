package nrega.worker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nrega.worker.Model.FamilyMember;
import nrega.worker.R;

/**
 * Created by HP on 22-01-2018.
 */

public class DemandMemberAdapter extends RecyclerView.Adapter<DemandMemberAdapter.MyViewHolder>{

    private List<FamilyMember> members;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView member_name,member_gender;

        public MyViewHolder(View itemView) {
            super(itemView);
            member_name = (TextView) itemView.findViewById(R.id.member_name);
            member_gender = (TextView) itemView.findViewById(R.id.member_gender);
        }
    }

    public DemandMemberAdapter(List<FamilyMember> members,Context context) {
        this.members = members;
        this.context = context;
    }

    @Override
    public DemandMemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.demand_member,viewGroup,false);

        return new DemandMemberAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DemandMemberAdapter.MyViewHolder myViewHolder, int position) {
        FamilyMember member = members.get(position);

        myViewHolder.member_name.setText(member.getName());
        myViewHolder.member_gender.setText(member.getGender());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
