package nrega.worker.Fragments;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nrega.worker.Adapters.ApplicationStatusAdapter;
import nrega.worker.Adapters.PaymentStatusAdapter;
import nrega.worker.Model.ApplicationStatus;
import nrega.worker.Model.PaymentStatus;
import nrega.worker.R;


public class CheckStatusFragment extends Fragment
{

    private List<ApplicationStatus> applicationStatusList=new ArrayList<>();
    private List<PaymentStatus> paymentStatusList=new ArrayList<>();
    private RecyclerView application_status_recyclerView,payment_status_recyclerView;
    private ApplicationStatusAdapter aSAdapter;
    private PaymentStatusAdapter pSAdapter;


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
        String[] values={"Select Status Type:","Application Status","Payment Status"};
        Spinner spinner=(Spinner) view.findViewById(R.id.spinner);
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
               // String s=parent.getItemAtPosition(position).toString();
              //  Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                if (position == 1)
                {
                    prepareApplicationStatusData();

                }
                else if (position == 2)
                {
                    preparePaymentStatusData();

                }

            }

            private void prepareApplicationStatusData(){
                ApplicationStatus appstatus=new ApplicationStatus("mohan","tilak nagar","alloted","2018-02-01","2018-02-12");
                applicationStatusList.add(appstatus);

            }

            private void preparePaymentStatusData(){
                PaymentStatus paymentstatus=new PaymentStatus("mohan","Due","2018-02-01","2018-02-12");
                paymentStatusList.add(paymentstatus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    };



}
