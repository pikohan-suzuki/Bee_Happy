package com.amebaownd.pikohan_nwiatori.beehappyclient

import android.content.res.AssetFileDescriptor
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket

class MainViewModel : ViewModel(){

    private var _imageResource = MutableLiveData<Int>()
    val imageResource : LiveData<Int> = _imageResource

    private lateinit var socket :DatagramSocket
    private lateinit var mMediaPlayer: MediaPlayer

    private var isPlayerCreated = false

   fun onPlayButtonClicked() {
        if (isPlayerCreated) {
            viewModelScope.launch(Dispatchers.IO) {
                play()
                _imageResource.postValue(R.drawable.bee_happy_1)
                runBlocking {
                    launch {
                        delay(6000)
                        _imageResource.postValue(R.drawable.bee_happy_2)
                    }
                    delay(8300)
                    _imageResource.postValue(R.drawable.blank)
                    stop()
                }
            }
        }
    }

    fun onStopButtonClicked() {
        stop()
    }

    fun setup(descriptor : AssetFileDescriptor) {
        mMediaPlayer = MediaPlayer()
        socket = DatagramSocket(28211)
        try {
            mMediaPlayer.setDataSource(
                descriptor.fileDescriptor,
                descriptor.startOffset,
                descriptor.length
            )
            mMediaPlayer.prepare()
            isPlayerCreated = true
        } catch (e: IOException) {
            e.printStackTrace()
        }

        viewModelScope.launch(Dispatchers.IO) {
            while(true){
                val buffer = ByteArray(16)
                val packet = DatagramPacket(buffer, buffer.size)
                val received = socket.receive(packet)
                onPlayButtonClicked()
            }
        }
    }

    private fun play() {
        mMediaPlayer.seekTo(188500)
        mMediaPlayer.start()
    }

    private fun stop() {
        mMediaPlayer.let {
            it.stop()
            it.prepare()
            _imageResource.postValue(R.drawable.blank)
        }
    }
}