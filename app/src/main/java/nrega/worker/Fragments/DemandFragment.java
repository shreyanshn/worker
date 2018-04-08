package nrega.worker.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nrega.worker.Adapters.NamesAdapter;
import nrega.worker.Constants.Constants;
import nrega.worker.DemandConfirmation;
import nrega.worker.R;
import nrega.worker.Utils.Utils;

public class DemandFragment extends Fragment {
    public DemandFragment() {
        // Required empty public constructor
    }
    DatePickerDialog datePickerDialog;
    TextView dateHeading;
    ArrayList<JSONObject> namesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NamesAdapter namesAdapter;
    Context appContext;
    GridLayoutManager layoutManager;
    AQuery aq;
    Button next;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean isLoading = true;
    String jobcardNum;
    String currentDateTimeString;
    private DatePicker dpResult;
    private Button btnChangeDate, btnChangeDate2;
    private int year;
    private int month;
    private int day;
    private TextView tvDisplayDate, tvDisplayDate2;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int cur = 0;
    int PageNo =1;
    String panchayatCode;
    String startDate,endDate;
    CalendarView calendarView;
    Calendar myCalendar,myCalendar2;
    ProgressBar markerProgress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        NamesAdapter nm;
        appContext = getContext();
        jobcardNum = Utils.getSharedPreferences(appContext,"jobcardNum");
        panchayatCode = Utils.getSharedPreferences(appContext,"panchayatCode");
        return inflater.inflate(R.layout.fragment_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        markerProgress = (ProgressBar) view.findViewById(R.id.marker_progress_demand);

        next = (Button) getView().findViewById(R.id.confirm_demand);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<namesAdapter.data.size();i++) {
                    if(namesAdapter.params.get(i)==1)
                    {
                        try {
                            final Map<String,Object> obj= new HashMap<String,Object>();
                            obj.put("panchayatCode",panchayatCode);
                            obj.put("jobcardNum",jobcardNum);
                            obj.put("startDate",startDate);
                            obj.put("endDate",endDate);
                            obj.put("applicantNum",i+1);
//                            Toast.makeText(appContext, obj.toString(), Toast.LENGTH_SHORT).show();
                            markerProgress.setVisibility(View.VISIBLE);
                            AQuery aQuery = new AQuery(appContext);

                            obj.put("jobcardNum",jobcardNum);
                            String url = Constants.base_url + "demandWork";
                            aQuery.ajax(url,obj,JSONObject.class,new  AjaxCallback<JSONObject>(){

                                @Override
                                public void callback(String url, JSONObject object, AjaxStatus status) {


                                    super.callback(url, object, status);
                                    if (object!=null){
                                        markerProgress.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_SHORT).show();
                                        /////////complete job demand//////////
                                        //       Toast.makeText(NamesActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                                        try {
                                            String key = object.getString("error");
                                            if(key.equals("false")) {
                                                String demandId = object.getString("demandId");
                                                Intent i = new Intent(getActivity(), DemandConfirmation.class);
                                                i.putExtra("demandId",demandId);
                                                i.putExtra("startDate",startDate);
                                                i.putExtra("endDate",endDate);
                                                startActivity(i);
                                                Toast.makeText(getActivity(), object.getString("demandId"), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), "Demand Already Exists", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        markerProgress.setVisibility(View.INVISIBLE);
                                        if (status.getCode() == AjaxStatus.NETWORK_ERROR) {
                                            Toast.makeText(getActivity(), "Please Check internet Connection", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), status.getCode() + "zxcvbnm", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                            //intent.putExtra("jobcardNum",jobcardNum);
                            //startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }

            }
        });


        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        btnChangeDate = (Button) view.findViewById(R.id.btn_set_date);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, month);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };

        btnChangeDate2 = (Button) view.findViewById(R.id.btn_set_date_2);
        btnChangeDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        init();
        appContext = getContext();
    }


    private void updateLabel1() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvDisplayDate = (TextView) getView().findViewById(R.id.from_date);
        tvDisplayDate.setText(sdf.format(myCalendar.getTime()));

        Toast.makeText(appContext, sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
        startDate = tvDisplayDate.getText().toString();
    }


    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tvDisplayDate2 = (TextView) getView().findViewById(R.id.till_date);
        tvDisplayDate2.setText(sdf.format(myCalendar2.getTime()));
        Toast.makeText(appContext, sdf.format(myCalendar2.getTime()), Toast.LENGTH_SHORT).show();
        endDate = tvDisplayDate2.getText().toString();
    }


    private void init() {
        namesList = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyler_names);
        namesAdapter = new NamesAdapter(appContext,namesList);
        layoutManager=new GridLayoutManager(appContext,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(namesAdapter);
        confirmApi();
    }

    private void confirmApi() {
        markerProgress.setVisibility(View.VISIBLE);

        AQuery aq;
        isLoading=false;
        String url = Constants.base_url +"members";
        aq = new AQuery(getContext());
        Map<String,Object> params= new HashMap<String,Object>();
        params.put("jobcardNum",jobcardNum);

        aq.ajax(url,params,JSONObject.class,new  AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {

                super.callback(url, object, status);
                isLoading=true;
                if (object!=null){
                    /////////send data to recycler view here//////////
                    Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        markerProgress.setVisibility(View.INVISIBLE);

                        String key = object.getString("error");
                        if(key.equals("false")) {
                            JSONArray array = object.getJSONArray("workers");
                            int i = 0;
                            while (i < array.length()) {
                                namesList.add(array.getJSONObject(i));
                                i++;
                            }
                            namesAdapter.notifyDataSetChanged();
                            PageNo++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (status.getCode() == AjaxStatus.NETWORK_ERROR) {
                        Toast.makeText(getActivity(), "Please Check internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), status.getCode() + "zxcvbnm", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
