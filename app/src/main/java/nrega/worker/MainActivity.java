package nrega.worker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import nrega.worker.Fragments.CheckStatusFragment;
import nrega.worker.Fragments.DemandFragment;
import nrega.worker.Fragments.Ejobcard_fragment;
import nrega.worker.Model.JobCard;
import nrega.worker.Utils.Utils;

public class MainActivity extends AppCompatActivity
{
    private ActionBar toolbar;
    JobCard jobCard;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mIntent  = getIntent();
        jobCard  = (JobCard) mIntent.getParcelableExtra("JobCard");
        Utils.setSharedPreference(this,"jobcardNum",jobCard.getJobCardNumber());
        toolbar=getSupportActionBar();
        BottomNavigationView navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new Ejobcard_fragment(jobCard);
        loadFragment(fragment);
        toolbar.setTitle(R.string.e_jobcard);
    }




    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment fragment;
                    switch (item.getItemId())
                    {
                        case R.id.check_status:
                            fragment = new CheckStatusFragment();
                            loadFragment(fragment);
                            toolbar.setTitle(R.string.check_status);
                            return true;
                        case R.id.e_jobcard:
                            fragment = new Ejobcard_fragment(jobCard);
                            loadFragment(fragment);
                            toolbar.setTitle(R.string.e_jobcard);
                            return true;
                        case R.id.demand_job:
                            fragment = new DemandFragment();
                            loadFragment(fragment);
                            toolbar.setTitle(R.string.demand_job);
                            return true;
                    }
                    return false;
                }
    };
}
