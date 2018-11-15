package com.example.fajrul.hitungbeasiswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void onClick (View v){
        if(v.getId()==R.id.mulai){
            Intent intent = new Intent(this, HitungActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

    }
}
