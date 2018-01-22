package nrega.worker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nrega.worker.Adapters.FamilyMemberAdapter;
import nrega.worker.Fragments.Ejobcard_fragment;
import nrega.worker.Model.FamilyMember;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ejobcard_fragment);
//        do not forget to comment the above line and uncomment the below line
//        setContentView(R.layout.activity_main);

        test();
    }

    private List<FamilyMember> memberList = new ArrayList<>();
    private RecyclerView family_member_recyclerView;
    private FamilyMemberAdapter family_member_adapter;

    private void test() {
        family_member_recyclerView = (RecyclerView)findViewById(R.id.family_member_recyclerView);

        family_member_adapter = new FamilyMemberAdapter(memberList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
