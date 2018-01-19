package nrega.worker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nrega.worker.Fragments.Ejobcard_fragment;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ejobcard_fragment);
//        do not forget to comment the above line and uncomment the below line
//        setContentView(R.layout.activity_main);


    }
}
