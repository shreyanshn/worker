package nrega.worker;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import nrega.worker.Fragments.DemandFragment;
import nrega.worker.Fragments.Ejobcard_fragment;

public class MainActivity extends AppCompatActivity
{
     private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=getSupportActionBar();
        BottomNavigationView navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Demand Job");
        loadFragment(new DemandFragment());
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, new DemandFragment());
//        transaction.addToBackStack(null);
//        transaction.commit();
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
                            toolbar.setTitle("Check Status");
                            return true;
                        case R.id.e_jobcard:
                            fragment = new Ejobcard_fragment();
                            loadFragment(fragment);
                            toolbar.setTitle("E-JobCard");
                            return true;
                        case R.id.demand_job:
                            fragment = new DemandFragment();
                            loadFragment(fragment);
                            toolbar.setTitle("Demand JoB");
                            return true;
                    }
                    return false;
                }
    };
}
