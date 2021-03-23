package com.example.proyectoiib_moviesapp.ui.home.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectoiib_moviesapp.Alquiler
import com.example.proyectoiib_moviesapp.AuthPelicula
import com.example.proyectoiib_moviesapp.R
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dataVideo.api.*
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.CategoryViewModel
import com.example.proyectoiib_moviesapp.ui.home.PopularMoviePagedListAdapter
import com.example.proyectoiib_moviesapp.ui.home.actors.ActorMoviePagedListAdapter
import com.example.proyectoiib_moviesapp.ui.home.actors.ActorPagedListRepository
import com.example.proyectoiib_moviesapp.ui.home.actors.ActorViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import id.ionbit.ionalert.IonAlert
import java.io.File


class MovieDetails : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private var viewModel2: ActorViewModel? = null
    private lateinit var movieRepository: MovieDetailsRepository
    private var actorRepository: ActorPagedListRepository?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository =
            MovieDetailsRepository(
                apiService
            )

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
            AuthPelicula.pelicula = it
        })

        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            findViewById<ProgressBar>(R.id.progress_bar).visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.txt_error).visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })


        actorRepository = ActorPagedListRepository(apiService)
        viewModel2 = getViewModel2(movieId)

        val movieAdapter = ActorMoviePagedListAdapter(this)

        val gridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)


        val rv_actor_list = findViewById<RecyclerView>(R.id.rv_actor_list)
        rv_actor_list.layoutManager = gridLayoutManager
        rv_actor_list.setHasFixedSize(false)
        rv_actor_list.adapter = movieAdapter


        viewModel2!!.actorPagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })



        val reproducir = findViewById<Button>(R.id.btn_play)
        reproducir.setOnClickListener{
            val intent = Intent(this, VideoPlay::class.java)
            intent.putExtra("id", movieId)
            this.startActivity(intent)
        }

        val descargar = findViewById<Button>(R.id.btn_download)
        descargar.setOnClickListener{
            descargarM(movieId)
        }

        val alquilar = findViewById<Button>(R.id.btn_alquilar)
        alquilar.setOnClickListener{
            val intent = Intent(this, Alquiler::class.java)
            this.startActivity(intent)
        }
    }

    fun bindUI( it: Pelicula){
        findViewById<TextView>(R.id.movie_title).text = it.nombre
        findViewById<TextView>(R.id.movie_tagline).text = it.subTitulo
        findViewById<TextView>(R.id.movie_release_date).text = it.fechaLanzamiento
        findViewById<TextView>(R.id.movie_rating).text = it.votosPromedio.toString() + "/10"
        findViewById<RatingBar>(R.id.ratingBar).rating = it.votosPromedio.toFloat()/2
        findViewById<TextView>(R.id.movie_runtime).text = it.duracion.toString() + " minutos"
        findViewById<TextView>(R.id.movie_overview).text = it.descripcion

        val moviePosterURL = POSTER_BASE_URL + it.uriPortada
        Glide.with(this)
            .load(moviePosterURL)
            .into(findViewById<ImageView>(R.id.iv_movie_poster));
    }

    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }

    private fun getViewModel2(movieId:Int): ActorViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ActorViewModel(actorRepository!!,
                movieId) as T
            }
        })[ActorViewModel::class.java]
    }

    private fun descargarM(movieId: Int){

        val storageRef = Firebase.storage.reference

        val movie = storageRef.child("501312.jpg")

        val localFile = File.createTempFile("descarga", "jpg")

        movie.getFile(localFile).addOnSuccessListener {
            Toast.makeText(this,"Descargando...",Toast.LENGTH_SHORT).show()
            Log.i("info","descargando!")
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