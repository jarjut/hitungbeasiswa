package com.example.fajrul.hitungbeasiswa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class OutputActivity extends AppCompatActivity {

    TextView textViewBeasiswa;
    TextView textViewIPK;
    TextView textViewGaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        textViewBeasiswa = findViewById(R.id.textViewBeasiswa);
        textViewGaji = findViewById(R.id.textViewGaji);
        textViewIPK = findViewById(R.id.textViewIPK);

        int beasiswa = (int) getIntent().getDoubleExtra("hasil",0);
        int gaji = (int) getIntent().getDoubleExtra("gaji",0);
        double ipk = getIntent().getDoubleExtra("ipk",0);

        String strBeasiswa = numberFormat.format(beasiswa);
        textViewBeasiswa.setText("Rp. "+strBeasiswa);

        String strGaji = numberFormat.format(gaji);
        textViewGaji.setText("Gaji OrangTua : Rp. "+strGaji);
        textViewIPK.setText("Indeks Prestasi : "+ipk);
    }

    public void onClick (View v){
        Intent intent = new Intent(this, HitungActivity.class);
        startActivity(intent);
        finish();
    }
}
