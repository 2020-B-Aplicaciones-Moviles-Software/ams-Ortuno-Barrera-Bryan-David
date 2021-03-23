package com.example.proyectoiib_moviesapp.dataVideo.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.proyectoiib_moviesapp.dataVideo.api.TheMovieDBInterface
import com.example.proyectoiib_moviesapp.dto.Pelicula
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Pelicula>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Pelicula> {

        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}