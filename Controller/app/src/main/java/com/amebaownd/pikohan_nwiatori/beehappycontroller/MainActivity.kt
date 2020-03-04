package com.amebaownd.pikohan_nwiatori.beehappycontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private val job = Job()
    private val coroutineContext = Dispatchers.Main + job
    private val scope = CoroutineScope(coroutineContext)
    private lateinit var socket : DatagramSocket
    private lateinit var address : InetAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        socket = DatagramSocket(28211)

        set_button.setOnClickListener {
            setAddress()
        }

        send_button.setOnClickListener {
            sendSign()
        }
    }

    private fun setAddress(){
        address =  InetAddress.getByName(address_editText.text.toString())
    }

    private fun sendSign(){
        scope.launch(Dispatchers.IO){
            val byte = "BeeHappy".toByteArray()
            val packet = DatagramPacket(byte,byte.size,address,28211)
            socket.send(packet)
        }
    }


}
