package nrega.worker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import nrega.worker.Model.JobCard;

public class DemandConfirmation extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demand_confirmation);
        toolbar=getSupportActionBar();
        toolbar.setTitle("Demand Confirmation");
        Intent mIntent  = getIntent();
       // mIntent.getExtras("startDate","endDate");
//        Toast.makeText(DemandConfirmation.this, obj.toString(), Toast.LENGTH_SHORT).show();

    }
}