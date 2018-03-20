package nrega.worker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class DemandConfirmation extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demand_confirmation);
        toolbar=getSupportActionBar();
        toolbar.setTitle("Demand Confirmation");


    }
}