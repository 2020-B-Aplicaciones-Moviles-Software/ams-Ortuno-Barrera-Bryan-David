package com.example.proyectoiib_moviesapp.ui.home.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib_moviesapp.R
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dataVideo.api.GENRE
import com.example.proyectoiib_moviesapp.dataVideo.api.QUERY
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBClient
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.ui.home.CategoryViewModel
import com.example.proyectoiib_moviesapp.ui.home.DemoObjectFragment
import com.example.proyectoiib_moviesapp.ui.home.MoviePagedListRepository
import com.example.proyectoiib_moviesapp.ui.home.PopularMoviePagedListAdapter

class Busqueda : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    lateinit var movieRepository: MoviePagedListRepositoryS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda)

        val nombre= intent.getStringExtra("nombre").toString()
        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePagedListRepositoryS(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };

        val rv_movie_list = findViewById<RecyclerView>(R.id.rv_movie_list)
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        GENRE = ""
        QUERY = nombre
        rv_movie_list.adapter = movieAdapter

        val progress_bar_popular = findViewById<ProgressBar>(R.id.progress_bar_popular)
        val txt_error_popular = findViewById<TextView>(R.id.txt_error_popular)

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })


    }

    private fun getViewModel(): SearchViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(movieRepository) as T
            }
        })[SearchViewModel::class.java]
    }


}