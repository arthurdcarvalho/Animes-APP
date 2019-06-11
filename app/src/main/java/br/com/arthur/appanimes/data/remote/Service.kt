package br.com.arthur.appanimes.data.remote

import br.com.arthur.appanimes.model.Film
import br.com.arthur.appanimes.model.People
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("films")
    fun getFilmsAsync(): Deferred<Response<List<Film>>>

    @GET("/films/{id}")
    fun getFilmByIdAsync(@Path("id") id: String): Deferred<Response<Film>>

    @GET("/people")
    fun getPeoplesAsync(): Deferred<Response<List<People>>>

}