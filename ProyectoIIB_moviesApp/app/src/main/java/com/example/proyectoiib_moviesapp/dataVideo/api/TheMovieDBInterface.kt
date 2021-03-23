package com.example.proyectoiib_moviesapp.dataVideo.api

import com.example.proyectoiib_moviesapp.dto.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<Pelicula>

    @GET("search/movie")
    fun getMovieSearch(@Query("page") page:Int): Single<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(@Path("movie_id") id: Int): Single<ActorResponse>

}