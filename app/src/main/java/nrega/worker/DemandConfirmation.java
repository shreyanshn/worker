package nrega.worker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import nrega.worker.Model.JobCard;
import nrega.worker.Utils.Utils;

public class DemandConfirmation extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demand_confirmation);
        toolbar=getSupportActionBar();
        toolbar.setTitle("Demand Confirmation");
        Intent mIntent  = getIntent();
        String startDate = mIntent.getStringExtra("startDate");
        String endDate = mIntent.getStringExtra("endDate");
        String demandId = mIntent.getStringExtra("demandId");

        TextView demand_id_text = (TextView) findViewById(R.id.demand_id_value);
        demand_id_text.setText(demandId);


        TextView jno_text = (TextView) findViewById(R.id.demand_jobcard_no_value);
        jno_text.setText(Utils.getSharedPreferences(this,"JobCardNo"));

        TextView name_text = (TextView) findViewById(R.id.demand_name_value);

        TextView fid_text = (TextView) findViewById(R.id.demand_familyid_value);
        fid_text.setText(Utils.getSharedPreferences(this,"familyId"));

        TextView category_text = (TextView) findViewById(R.id.demand_category_value);
        fid_text.setText(Utils.getSharedPreferences(this,"category"));

        TextView address_text = (TextView) findViewById(R.id.demand_address_value);
        fid_text.setText(Utils.getSharedPreferences(this,"address"));

        TextView start_date_text = (TextView) findViewById(R.id.demand_start_date_value);
        start_date_text.setText(startDate);

        TextView end_date_text = (TextView) findViewById(R.id.demand_end_date_value);
        end_date_text.setText(endDate);

       // mIntent.getExtras("startDate","endDate");
//        Toast.makeText(DemandConfirmation.this, obj.toString(), Toast.LENGTH_SHORT).show();

    }
}