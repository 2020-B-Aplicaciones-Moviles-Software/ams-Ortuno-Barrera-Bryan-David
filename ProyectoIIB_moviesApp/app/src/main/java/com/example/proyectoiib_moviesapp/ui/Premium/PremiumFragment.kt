package com.example.proyectoiib_moviesapp.ui.Premium

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectoiib_moviesapp.AuthUsuario
import com.example.proyectoiib_moviesapp.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.*
import id.ionbit.ionalert.IonAlert
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PremiumFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_premium, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val remoteModel = FirebaseCustomRemoteModel.Builder("Detector-Fraude").build()
        val conditions = FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build()
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener {
                    //Descarga Actualizaciones
                }

        val localModel = FirebaseCustomLocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build()

        val options = FirebaseModelInterpreterOptions.Builder(localModel).build()
        val interpreter = FirebaseModelInterpreter.getInstance(options)

        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnSuccessListener { isDownloaded ->
                    val options =
                            if (isDownloaded) {
                                FirebaseModelInterpreterOptions.Builder(remoteModel).build()
                            } else {
                                FirebaseModelInterpreterOptions.Builder(localModel).build()
                            }
                    val interpreter = FirebaseModelInterpreter.getInstance(options)
                }



        val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
                .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1,29))
                .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 1))
                .build()



        val input = ByteBuffer.allocateDirect(29*4).order(ByteOrder.nativeOrder())
        for (y in 0 until 1) {
            for (x in 0 until 29) {
                input.putFloat(AuthUsuario.usuario!!.datosPago!![x].toFloat())
                //input.putFloat(inputd2[x].toFloat())
            }
        }

        val inputs = FirebaseModelInputs.Builder()
                .add(input) // add() as many input arrays as your model requires
                .build()

        val button   = view.findViewById<Button>(R.id.btn_pago)
        button.setOnClickListener{

            interpreter?.run(inputs, inputOutputOptions)
                    ?.addOnSuccessListener { result ->
                        val output = result.getOutput<Array<FloatArray>>(0)
                        val probabilities = output[0]
                        for (i in probabilities.indices) {
                            Log.i("MLKit", String.format("%1.4f", probabilities[i]))
                            if(String.format("%1.4f", probabilities[i]) == "0.0000"){
                                accionCompletada()
                            }
                            else{
                                accionRechazada()
                            }
                        }
                    }
                    ?.addOnFailureListener { e ->
                        Log.i("Info","Ocurrió un error")
                    }
        }

    }


    fun accionCompletada(){
        IonAlert(context, IonAlert.SUCCESS_TYPE)
                .setTitleText("¡Buen trabajo!")
                .setContentText("Acción completada con éxito!")
                .show()
    }

    fun accionRechazada(){

        IonAlert(context, IonAlert.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("¡Algo salió mal!")
                .show()
    }
}