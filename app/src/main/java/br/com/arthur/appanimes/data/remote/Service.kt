package br.com.arthur.appanimes.data.remote

import br.com.arthur.appanimes.model.Film
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("films")
    fun getFilmsAsync(): Deferred<Response<List<Film>>>

    @GET("/films/{id}")
    fun getFilmById(@Path("id") id: String): Deferred<Response<Film>>

}