package com.example.fajrul.hitungbeasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HitungActivity extends AppCompatActivity {
    private double ipk, ipk_rendah, ipk_tinggi;
    private double gaji, g_rendah, g_tinggi;
    private double rule1, rule2, rule3, rule4;
    private double z1, z2, z3, z4;
    private double z;

    EditText editTextIPK;
    EditText editTextGaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung);

        editTextIPK = findViewById(R.id.editTextIPK);
        editTextGaji = findViewById(R.id.editTextGaji);
    }

    public void onClick(View v){
        String ipkx = editTextIPK.getText().toString();
        String gajix = editTextGaji.getText().toString();
        if(ipkx.isEmpty()){
            Toast.makeText(this, "Please fill in the form",Toast.LENGTH_SHORT).show();
        }else if(gajix.isEmpty()){
            Toast.makeText(this, "Please fill in the form",Toast.LENGTH_SHORT).show();
        }else if(Double.parseDouble(ipkx)>=3){
            ipk  = Double.parseDouble(ipkx);
            gaji = Double.parseDouble(gajix);
            double hasil = hitung(ipk, gaji);
            Intent intent = new Intent(this, OutputActivity.class);
            intent.putExtra("hasil", hasil);
            intent.putExtra("ipk",ipk);
            intent.putExtra("gaji",gaji);
            startActivity(intent);
            finish();
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Syarat Tidak Memenuhi");
            alertDialog.setMessage("IPK Minimal 3.0");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public double hitung(double ipk, double gaji){
        fuzzifikasiIPK(ipk);
        fuzzifikasiPenghasilanOrtu(gaji);
        Rules();
        return defuzzifikasi();
    }

    public void fuzzifikasiPenghasilanOrtu(double gaji){
        if(gaji <= 2000000){
            g_rendah =1;
            g_tinggi =0;
        }else if(gaji >= 2000000 && gaji <= 6000000){
            g_rendah = (6000000-gaji)/(6000000-2000000);
            g_tinggi = (gaji-2000000)/(6000000-2000000);
        }else{
            g_rendah =0;
            g_tinggi =1;
        }
        Log.d("gaji_rendah", ""+g_rendah);
        Log.d("gaji_tinggi", ""+g_tinggi);
    }

    public void fuzzifikasiIPK(double ipk){
        if(ipk <= 3){
            ipk_rendah=1;
            ipk_tinggi=0;
        }else if(ipk >= 3 && ipk <= 4){
            ipk_rendah = (4-ipk)/(4-3);
            ipk_tinggi = (ipk-3)/(4-3);
        }else{
            ipk_rendah=0;
            ipk_tinggi=1;
        }
        Log.d("ipk_rendah", ""+ipk_rendah);
        Log.d("ipk_tinggi", ""+ipk_tinggi);
    }

    public void Rules(){
        // IF Penghasilan Tinggi dan IPK TINGGI then Besar beasiswa Rendah
        rule1   = Math.min(g_tinggi, ipk_tinggi);
        z1 = 4000000 - (rule1 * (4000000 - 2000000));
        // IF Penghasilan Rendah dan IPK Rendah then Besar beasiswa Tinggi
        rule2   = Math.min(g_rendah, ipk_rendah);
        z2 = 2000000 + (rule2 * 4000000);
        // IF Penghasilan Rendah dan IPK Tinggi then Besar beasiswa Tinggi
        rule3   = Math.min(g_rendah, ipk_tinggi);
        z3 = 2000000 + (rule3 * 4000000);
        // IF Penghasilan Tinggi dan IPK Rendah then Besar beasiswa Rendah
        rule4   = Math.min(g_tinggi, ipk_rendah);
        z4 = 4000000 - (rule4 * (4000000 - 2000000));
    }

    public double defuzzifikasi(){
        return z = ((rule1*z1)+(rule2*z2)+(rule3*z3)+(rule4*z4))/(rule1+rule2+rule3+rule4);
    }




}
