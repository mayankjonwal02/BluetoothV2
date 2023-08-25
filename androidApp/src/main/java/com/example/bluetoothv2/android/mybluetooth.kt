package com.example.bluetoothv2.android

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class mybluetooth(private val context: Context) : ViewModel()  {


    var mybluetoothadapter = BluetoothAdapter.getDefaultAdapter()
//    var context = context
//    var hello = MutableLiveData<List<String>>(emptyList())
//    var data : LiveData<List<String>> get() = hello


    private val _demodata = MutableStateFlow<Int?>(0)
    val demodata = _demodata.asStateFlow()


    private val _demodata2 = MutableLiveData<Int?>(0)
    val demodata2 : LiveData<Int?> get() = _demodata2

  val _data = MutableStateFlow(emptyList<String>())
    val data: StateFlow<List<String>> = _data

    private val _discovereddevices = MutableLiveData<List<String>>(emptyList())
    val discoveredDevices  : LiveData<List<String>> get() = _discovereddevices

    private val _discovereddevicesaddr = MutableLiveData<List<String>>(emptyList())

    fun updatediscovereddevices(newdevices : List<String>)
    {
        _discovereddevices.value = emptyList()
        _discovereddevices.value = newdevices
//        _data.value = newdevices

    }

    private fun updatedata()
    {
//        viewModelScope.launch{
            _demodata2.value = _demodata2.value?.plus(1)
//        }

    }

    var btdiscoverbroadcast = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothDevice.ACTION_FOUND) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    val deviceName = it.name ?: "Unknown Device"
                    val deviceAddress = it.address

                    // Check if the device name already exists in the list
                    if (!discoveredDevices.value?.contains(deviceName)!! == true) {
                        // Add the device name to the list
                        val updatedDevices = discoveredDevices.value?.toMutableList() ?: mutableListOf()
                        updatedDevices.add(deviceName)

                        // Update the LiveData with the new list
                        _discovereddevices.value = updatedDevices
                    }
                }
            }
        }
    }

    init {
        var intent =  IntentFilter(BluetoothDevice.ACTION_FOUND)

        context.registerReceiver(btdiscoverbroadcast,intent)
    }



    @SuppressLint("MissingPermission")
    fun enableBluetooth(): Int {
        var mybluetoothadapter = BluetoothAdapter.getDefaultAdapter()
        var resultLauncher : ActivityResultLauncher<Intent>


        try{
            if (mybluetoothadapter == null) {
                Toast.makeText(context, "Bluetooth not Supported", Toast.LENGTH_LONG).show()
                return 0
            } else {
                if (!mybluetoothadapter.isEnabled) {
                    Toast.makeText(context, "Enabling Bluetooth", Toast.LENGTH_SHORT).show()
                    var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    context.startActivity(intent)
                    Toast.makeText(context, "Enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Already Enabled", Toast.LENGTH_SHORT).show()
                }

                return 1
            }

        }
        catch (e:Exception)
        {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            return 0
        }

    }

    @SuppressLint("MissingPermission")
    fun getpaireddevices(): List<BluetoothDevice> {
        updatedata()
        var a = mybluetoothadapter.bondedDevices.toList()
        return a
    }

    @SuppressLint("MissingPermission")
    fun startdiscovery() {

        var intent =  IntentFilter(BluetoothDevice.ACTION_FOUND)

        context.registerReceiver(btdiscoverbroadcast,intent)
        try{
            mybluetoothadapter.startDiscovery()
            Toast.makeText(context, "Discovery Started", Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception)
        {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}