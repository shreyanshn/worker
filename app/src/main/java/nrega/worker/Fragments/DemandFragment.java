package nrega.worker.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nrega.worker.Adapters.DemandMemberAdapter;
import nrega.worker.Adapters.FamilyMemberAdapter;
import nrega.worker.Model.FamilyMember;
import nrega.worker.R;

public class DemandFragment extends Fragment implements View.OnClickListener{

    private List<FamilyMember> memberList = new ArrayList<>();
    private RecyclerView demand_member_recyclerView;
    private DemandMemberAdapter demand_member_adapter;

    private TextView tv_start_date;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    public DemandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);

        demand_member_adapter = new DemandMemberAdapter(memberList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        demand_member_recyclerView.setLayoutManager(mLayoutManager);
        demand_member_recyclerView.setItemAnimator(new DefaultItemAnimator());
        demand_member_recyclerView.setAdapter(demand_member_adapter);

        tv_start_date.setOnClickListener(this);


    }

    private void findId(View view) {
        tv_start_date = (TextView)view.findViewById(R.id.tv_start_date);
        demand_member_recyclerView= (RecyclerView)view.findViewById(R.id.demand_work_members_recyclerview);

    }

    @Override
    public void onClick(View view) {
    }
}
