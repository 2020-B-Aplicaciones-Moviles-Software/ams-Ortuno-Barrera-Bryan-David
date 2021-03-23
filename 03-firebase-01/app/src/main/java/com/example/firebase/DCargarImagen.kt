package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_d_cargar_imagen.*
import java.io.ByteArrayOutputStream


class DCargarImagen : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private val CAMERA_ACTIVITY = 51
    private var imagenUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_cargar_imagen)

        btn_cargar_imagen.setOnClickListener{
            selectPhotoFromGalerry(this,SELECT_ACTIVITY)
        }
        btn_tomar_imagen.setOnClickListener{
            dispatchTakePictureIntent()
        }
        btn_subir_firebase.setOnClickListener{
            cargarFotoFirebase()
            //cargarImagenesURI()
        }
        btn_subir_desde_firebase.setOnClickListener{

            descargarImagen()
        }

    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA_ACTIVITY)
            }
        }
    }

    private fun selectPhotoFromGalerry(activity: Activity, code:Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,code)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imagenUri = data!!.data
                idImagen.setImageURI(imagenUri)
            }
            requestCode == CAMERA_ACTIVITY && resultCode == RESULT_OK -> {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                idImagen.setImageBitmap(imageBitmap)
            }
        }
    }

    fun cargarFotoFirebase(){
        // Create a storage reference from our app
        val storageRef = Firebase.storage.reference
        // Create a reference to "mountains.jpg"
        val imagenRef = storageRef.child("Fotos1/imagen2.jpg")


        idImagen.isDrawingCacheEnabled = true
        idImagen.buildDrawingCache()
        val bitmap = (idImagen.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imagenRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("firebase-uploadImage","Falló")
        }.addOnSuccessListener {
            Log.i("firebase-uploadImage","Se agregó")
        }
    }

    fun cargarImagenesURI(){
        // Create a storage reference from our app
        val storageRef = Firebase.storage.reference

       // var file = Uri.fromFile(File("${imagenUri}"))
        val riversRef = storageRef.child("fotos2/imagen.jpg")
        var uploadTask = riversRef.putFile(imagenUri!!)

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            Log.i("firebase-uploadImage","${imagenUri}")
        }.addOnSuccessListener {
            Log.i("firebase-uploadImage","${imagenUri}")
        }

    }

    fun descargarImagen(){
        val storageRef = Firebase.storage.reference

// Create a reference with an initial file path and name
        val nombre = id_editText.text.toString()
        if (nombre!=null){
            val pathReference = storageRef.child("Fotos1/${nombre}")

            val ONE_MEGABYTE: Long = 1024 * 1024
//        val imagen = pathReference.getBytes(ONE_MEGABYTE).getResult()
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                idImagen.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.i("firebase-download","no funca tu hueva")
            }

        }
        else{
            Log.i("firebase-download","campo vacio")
        }

    }

}
