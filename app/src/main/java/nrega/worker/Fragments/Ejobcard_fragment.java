package nrega.worker.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nrega.worker.Adapters.FamilyMemberAdapter;
import nrega.worker.CameraActivity;
import nrega.worker.Constants.Constants;
import nrega.worker.MainActivity;
import nrega.worker.Model.FamilyMember;
import nrega.worker.Model.JobCard;
import nrega.worker.R;
import nrega.worker.Utils.Utils;

@SuppressLint("ValidFragment")
public class Ejobcard_fragment extends Fragment {

    private List<FamilyMember> memberList = new ArrayList<>();
    private RecyclerView family_member_recyclerView;
    private FamilyMemberAdapter family_member_adapter;
    private TextView jno_text,fid_text,head_text,category_text,address_text,village_text,panchayat_text,block_text,district_text,father_text,text_days_left;
    private String jno,familyId,head,category,address,village,panchayat,block,district,father;
    private int daysLeft;
    ProgressBar markerProgress;


    @SuppressLint("ValidFragment")
    public Ejobcard_fragment(JobCard jobCard) {
        jno = jobCard.getJobCardNumber();
        familyId = jobCard.getFamilyId();
        head = jobCard.getHead();
        category = jobCard.getCategory();
        address = jobCard.getAddress();
        village = jobCard.getVillage();
        panchayat=jobCard.getPanchayat();
        block = jobCard.getBlock();
        district = jobCard.getDistrict();
        father = jobCard.getFather();
        daysLeft = jobCard.getDaysLeft();
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

        jno_text = (TextView) view.findViewById(R.id.e_jobcard_num);
        jno_text.setText(jno);

        fid_text = (TextView) view.findViewById(R.id.family_id);
        fid_text.setText(familyId);

        head_text = (TextView) view.findViewById(R.id.head_name);
        head_text.setText(head);

        category_text = (TextView) view.findViewById(R.id.category);
        category_text.setText(category);

        address_text = (TextView) view.findViewById(R.id.address);
        address_text.setText(address);

        village_text = (TextView) view.findViewById(R.id.address);
        village_text.setText(village);

        panchayat_text = (TextView) view.findViewById(R.id.panchayat);
        panchayat_text.setText(panchayat);

        block_text = (TextView) view.findViewById(R.id.block);
        block_text.setText(block);

        district_text = (TextView) view.findViewById(R.id.district);
        district_text.setText(district);

        father_text = (TextView) view.findViewById(R.id.father_name);
        father_text.setText(father);

        text_days_left = (TextView) view.findViewById(R.id.days_left);
        text_days_left.setText(""+daysLeft);

        testprepareDate();

    }

    private void testprepareDate() {
        markerProgress = (ProgressBar) getView().findViewById(R.id.marker_progress_jobcard);
        markerProgress.setVisibility(View.VISIBLE);
        String url = Constants.base_url + "members";
        Context context = getContext();
        AQuery aq = new AQuery(context);
        Map<String,Object> params = new HashMap<String,Object>();

        params.put("jobcardNum",jno);
        aq.ajax(url,params, JSONObject.class,new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                if (object != null) {

                    try {
                        markerProgress.setVisibility(View.INVISIBLE);
                        String key = object.getString("error");
                        if (key.equals("false")) {
                            //code here to parse the json object
                            // send data to recycler view
                            JSONArray array = object.getJSONArray("workers");
                            int i = 0;
                            FamilyMember fm;
                            while (i<array.length()){
                                JSONObject  jsonObject = array.getJSONObject(i);

                                fm = new FamilyMember(jsonObject.getString("workerName"),jsonObject.getString("age"),jsonObject.getString("Bank"));
                                memberList.add(fm);
                                i++;
                            }
                            family_member_adapter.notifyDataSetChanged();




                        } else {

                            Toast.makeText(getActivity(), "error true", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "JSONException ", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    if (status.getCode() == AjaxStatus.NETWORK_ERROR) {

                        //Check Internet Connection
                    } else {
                        Toast.makeText(getActivity(), "asdfg" + status.getCode(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });




    }

}
