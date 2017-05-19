package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var downloadBinder: MyService.DownloadBinder? = null
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder!!.startDownload()
            downloadBinder!!.progress
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startService = findViewById(R.id.start_service) as Button
        val stopService = findViewById(R.id.stop_service) as Button
        startService.setOnClickListener(this)
        stopService.setOnClickListener(this)
        val bindService = findViewById(R.id.bind_service) as Button
        val unbindService = findViewById(R.id.unbind_servce) as Button
        bindService.setOnClickListener(this)
        unbindService.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start_service -> {
                val startIntent = Intent(this, MyService::class.java)
                startService(startIntent)
            }
            R.id.stop_service -> {
                val stopIntent = Intent(this, MyService::class.java)
                stopService(stopIntent)
            }
            R.id.bind_service -> {
                val bindIntent = Intent(this, MyService::class.java)
                bindService(bindIntent, connection, Context.BIND_AUTO_CREATE)
            }
            R.id.unbind_servce -> unbindService(connection)//解绑服务
            else -> {
            }
        }
    }
}







