package nrega.worker.Fragments;

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

import java.util.ArrayList;
import java.util.List;

import nrega.worker.Adapters.FamilyMemberAdapter;
import nrega.worker.Model.FamilyMember;
import nrega.worker.R;

public class Ejobcard_fragment extends Fragment {

    private List<FamilyMember> memberList = new ArrayList<>();
    private RecyclerView family_member_recyclerView;
    private FamilyMemberAdapter family_member_adapter;

    public Ejobcard_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ejobcard_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        family_member_recyclerView = (RecyclerView)view.findViewById(R.id.family_member_recyclerView);

        family_member_adapter = new FamilyMemberAdapter(memberList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        family_member_recyclerView.setLayoutManager(mLayoutManager);
        family_member_recyclerView.setItemAnimator(new DefaultItemAnimator());
        family_member_recyclerView.setAdapter(family_member_adapter);

        testprepareDate();

    }

    private void testprepareDate() {
        FamilyMember fm = new FamilyMember("Harshit Koolwal","19 June 1996","SBI");
        memberList.add(fm);

        fm = new FamilyMember("Aastha Goyal","04 Nov 1996","ICICI");
        memberList.add(fm);
        fm = new FamilyMember("Sasnika Patil","03 March 1997","HDFC");
        memberList.add(fm);

        family_member_adapter.notifyDataSetChanged();
    }
}
