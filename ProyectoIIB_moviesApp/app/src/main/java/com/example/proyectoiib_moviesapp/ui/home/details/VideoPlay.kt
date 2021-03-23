package com.example.proyectoiib_moviesapp.ui.home.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import com.example.proyectoiib_moviesapp.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import id.ionbit.ionalert.IonAlert

class VideoPlay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        val movieId: Int = intent.getIntExtra("id",1)

        obtenerVideo(movieId)

    }

    private fun obtenerVideo(movieId: Int){

        val storageRef = Firebase.storage.reference

        val pathReference = storageRef.child("${movieId}.mp4")
        pathReference.downloadUrl.addOnSuccessListener {
            val videoView = findViewById<VideoView>(R.id.videoView)
            val mediaController = MediaController(this)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(it)
            videoView.requestFocus()
            videoView.start()
        }.addOnFailureListener {
            accionRechazada()
        }

    }

    fun accionRechazada(){

        IonAlert(this, IonAlert.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText("¡El video no está disponible!, \nIntentalo de nuevo más tarde")
            .show()
    }
}