package nrega.worker.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import nrega.worker.Adapters.ApplicationStatusAdapter;
import nrega.worker.Adapters.PaymentStatusAdapter;
import nrega.worker.Constant.Constant;
import nrega.worker.Model.ApplicationStatus;
import nrega.worker.Model.PaymentStatus;
import nrega.worker.R;
import nrega.worker.Utils.Utils;


public class CheckStatusFragment extends Fragment
{

    private List<ApplicationStatus> applicationStatusList=new ArrayList<>();
    private List<PaymentStatus> paymentStatusList=new ArrayList<>();
    private RecyclerView application_status_recyclerView,payment_status_recyclerView;
    private ApplicationStatusAdapter aSAdapter;
    private PaymentStatusAdapter pSAdapter;
    ProgressBar markerProgress;

    public CheckStatusFragment()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_check_status, container, false);
    }

    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        String[] values={"Select Status Type:",getString(R.string.application_status),getString(R.string.payment_status)};
        final Spinner spinner=(Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.spinner,values){


            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View v=super.getDropDownView(position,convertView,parent);
                TextView tv=(TextView) v;
              /*  int selected=spinner.getSelectedItemPosition();
                if(position==selected){
                    spinner.setBackgroundColor(Color.GREEN);

                }
                else
                    v.setBackgroundColor(Color.WHITE);*/
                if(position==0)
                    tv.setTextColor(Color.GRAY);
                else
                    tv.setTextColor(Color.BLACK);
            return v;
            }

        };



        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        application_status_recyclerView = (RecyclerView) view.findViewById(R.id.application_status_recyclerView);
        aSAdapter = new ApplicationStatusAdapter(applicationStatusList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        application_status_recyclerView.setLayoutManager(layoutManager);
        application_status_recyclerView.setItemAnimator(new DefaultItemAnimator());
        application_status_recyclerView.setAdapter(aSAdapter);

        payment_status_recyclerView = (RecyclerView) view.findViewById(R.id.payment_status_recyclerView);
        pSAdapter = new PaymentStatusAdapter(paymentStatusList);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        payment_status_recyclerView.setLayoutManager(layoutManager1);
        payment_status_recyclerView.setItemAnimator(new DefaultItemAnimator());
        payment_status_recyclerView.setAdapter(pSAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                markerProgress = (ProgressBar) getView().findViewById(R.id.progress_check_status);

                //String s=parent.getItemAtPosition(position).toString();
             // Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                //((TextView) view).setTextColor(Color.GREEN);
              //  int item = (int) parent.getItemAtPosition(position);
                //((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorGreen));
                if (position == 1)
                {
                    markerProgress.setVisibility(View.VISIBLE);


                    payment_status_recyclerView.setVisibility(View.GONE);
                    application_status_recyclerView.setVisibility(View.VISIBLE);
                    String url = Constant.base_url + "demandDetails";
                    Context context = getContext();
                    AQuery aq = new AQuery(context);
                    String jobcardNum = Utils.getSharedPreferences(context,"jobcardNum");

                    Map<String,Object> params = new HashMap<String,Object>();

                    params.put("jobcardNum",jobcardNum);
                    aq.ajax(url,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            Toast.makeText(getActivity(),object.toString()+"appstatus",Toast.LENGTH_LONG).show();
                            if (object != null) {

                                try {
                                    markerProgress.setVisibility(View.INVISIBLE);
                                    String key = object.getString("error");
                                    if (key.equals("false")) {
                                        //code here to parse the json object
                                        // send data to recycler view
                                        JSONArray array = object.getJSONArray("demands");
                                        int i = 0;
                                        ApplicationStatus as;
                                        while (i<array.length()){
                                            JSONObject  jsonObject = array.getJSONObject(i);

                                            as= new ApplicationStatus(jsonObject.getString("workerName"),"Tubewell",jsonObject.getString("status"),jsonObject.getString("startDate"),jsonObject.getString("endDate"));
                                            applicationStatusList.add(as);
                                            i++;
                                        }
                                        aSAdapter.notifyDataSetChanged();




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
                else if (position == 2)
                {
                   application_status_recyclerView.setVisibility(View.GONE);
                    payment_status_recyclerView.setVisibility(View.VISIBLE);
                    markerProgress.setVisibility(View.VISIBLE);

                    //    PaymentStatus paymentstatus=new PaymentStatus("Mohan","Due","2018-02-01","2018-02-12");
                    String url = Constant.base_url + "previousJobDetails";
                    Context context = getContext();
                    AQuery aq = new AQuery(context);
                    String jobcardNum = Utils.getSharedPreferences(context,"jobcardNum");
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("jobcardNum",jobcardNum);
                    aq.ajax(url,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            Toast.makeText(getActivity(),object.toString()+"paystatus",Toast.LENGTH_LONG).show();
                            if (object != null) {

                                try {
                                    markerProgress.setVisibility(View.INVISIBLE);

                                    String key = object.getString("error");
                                    if (key.equals("false")) {
                                        //code here to parse the json object
                                        // send data to recycler view
                                        JSONArray array = object.getJSONArray("works");
                                        int i = 0;
                                        PaymentStatus ps;
                                        while (i<array.length()){
                                            JSONObject  jsonObject = array.getJSONObject(i);

                                            ps= new PaymentStatus(jsonObject.getString("applicantNum"),jsonObject.getString("paymentDone"),jsonObject.getString("startDateOfWorker"),jsonObject.getString("endDate"));
                                            paymentStatusList.add(ps);
                                            i++;
                                        }
                                        pSAdapter.notifyDataSetChanged();




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





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    };



}
