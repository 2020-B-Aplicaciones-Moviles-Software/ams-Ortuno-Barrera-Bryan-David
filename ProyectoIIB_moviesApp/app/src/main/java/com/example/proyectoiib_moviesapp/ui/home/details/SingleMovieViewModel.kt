package com.example.proyectoiib_moviesapp.ui.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.details.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<Pelicula> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}