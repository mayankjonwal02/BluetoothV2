package com.example.bluetoothv2.android

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

//import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("MissingPermission")
@Composable
fun UI(bluetooth : mybluetooth) {
    var permissionlauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(), onResult = {})
    LaunchedEffect(Unit){
        permissionlauncher.launch(
            arrayOf(
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_ADVERTISE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    

    var btkey by remember {
        mutableStateOf(0)
    }
    var context = LocalContext.current
    

    val demodata by bluetooth.demodata2.observeAsState(initial = 0)
    var devicelist by remember {
        mutableStateOf( emptyList<String>())
    }

    val discovereddevices by bluetooth.discoveredDevices.observeAsState(initial = emptyList())

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan), contentAlignment = Alignment.Center)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Button(
                onClick = { btkey = bluetooth.enableBluetooth() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Enable Bluetooth")
            }

            Spacer(modifier = Modifier.height(20.dp))
            if(btkey == 1)
            {
                Button(
                    onClick = {
                              devicelist = emptyList<String>()
                        devicelist = bluetooth.getpaireddevices().mapNotNull {
                            device -> device.name
                        }
                              },

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Get Paired devices")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = devicelist.toString(), color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        bluetooth.startdiscovery()
                    },

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Start Discovery")
                }
                Spacer(modifier = Modifier.height(20.dp))
//               Text()


            }
            Text(text = discovereddevices.toString())

        }
    }


}