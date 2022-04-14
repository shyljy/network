package com.example.network;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isWiFi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            if (networkInfo != null && networkInfo.isAvailable()){
                Log.i(TAG, "onReceive: 网络已连接");
                if(isWiFi){
                  Toast.makeText(context, "WIFI网络已连接", Toast.LENGTH_SHORT).show();

                } else {
                  Toast.makeText(context, "移动网络已连接", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i(TAG, "onReceive: 网络未连接");
                Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
            }
        }
    }
}