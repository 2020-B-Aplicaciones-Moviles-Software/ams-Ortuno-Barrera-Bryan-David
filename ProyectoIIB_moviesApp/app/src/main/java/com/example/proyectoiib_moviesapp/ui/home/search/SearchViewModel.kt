package com.example.proyectoiib_moviesapp.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.ui.home.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(private val movieRepository : MoviePagedListRepositoryS) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Pelicula>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}