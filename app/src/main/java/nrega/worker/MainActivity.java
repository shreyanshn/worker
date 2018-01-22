package nrega.worker;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

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


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {

                    switch (item.getItemId())
                    {
                        case R.id.check_status:
                            toolbar.setTitle("Check Status");
                            return true;
                        case R.id.e_jobcard:
                            toolbar.setTitle("E-JobCard");
                            return true;
                        case R.id.demand_job:
                            toolbar.setTitle("Demand JoB");
                            return true;

                    }
                    return false;
                }

    };

}
