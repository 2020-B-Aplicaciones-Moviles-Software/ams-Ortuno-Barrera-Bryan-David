package com.example.proyectoiib_moviesapp.ui.home.actors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.proyectoiib_moviesapp.dataVideo.Repository.NetworkState
import com.example.proyectoiib_moviesapp.dto.Actor
import io.reactivex.disposables.CompositeDisposable

class ActorViewModel(private val actorRepository : ActorPagedListRepository,movieId:Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  actorPagedList : LiveData<PagedList<Actor>> by lazy {
        actorRepository.fetchLiveMoviePagedList(compositeDisposable,movieId)
    }

//    val  networkState : LiveData<NetworkState> by lazy {
//        actorRepository.getNetworkState()
//    }

    fun listIsEmpty(): Boolean {
        return actorPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}