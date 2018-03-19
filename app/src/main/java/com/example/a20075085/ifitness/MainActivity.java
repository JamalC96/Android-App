package com.example.a20075085.ifitness;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment()
    {
        return new MainFragment();
    }

}
