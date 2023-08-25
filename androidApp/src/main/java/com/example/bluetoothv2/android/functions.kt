package com.example.bluetoothv2.android

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

@SuppressLint("MissingPermission")
fun enableBluetooth(context: Context, activity: Activity)
{
    var mybluetoothadapter = BluetoothAdapter.getDefaultAdapter()
    var resultLauncher : ActivityResultLauncher<Intent>


    if(mybluetoothadapter == null)
    {
        Toast.makeText(context,"Bluetooth not Supported",Toast.LENGTH_LONG).show()
    }
    else
    {
        if(!mybluetoothadapter.isEnabled)
        {
            Toast.makeText(context,"Enabling Bluetooth",Toast.LENGTH_SHORT).show()
            var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivity(intent)
        }
    }
}