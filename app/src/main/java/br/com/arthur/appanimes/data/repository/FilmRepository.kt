package br.com.arthur.appanimes.data.repository

import android.content.Context
import br.com.arthur.appanimes.data.local.dao.FilmDao
import br.com.arthur.appanimes.data.remote.ServiceFactory
import br.com.arthur.appanimes.model.Film

class FilmRepository(private val filmDao: FilmDao, context: Context) :
    BaseRepository(context) {

    private val service = ServiceFactory.getService()

    @Throws(ConnectionException::class)
    suspend fun getFilms(success: (List<Film>) -> Unit, failed: (String) -> Unit) {
        val filmsList = filmDao.getFilms()
        if (filmsList.isNotEmpty()) {
            success(filmsList)
        } else if (checkConnection()) {
            val response = service.getFilmsAsync().await()
            if (response.isSuccessful) {
                response.body().let { filmsResponse ->
                    run {
                        filmsResponse?.forEach { film ->
                            run {
                                filmDao.saveFilm(film)
                                success(filmsResponse)
                            }
                        }
                    }
                }
            } else {
                failed("Não foi possivel obter a lista de filmes")
            }
        } else {
            throw ConnectionException("Sem conexão com a internet")
        }
    }

    @Throws(ConnectionException::class)
    suspend fun getFilmById(id: String, success: (Film) -> Unit, failed: (String) -> Unit) {
        val film = filmDao.getFilm(id)
        when {
            film != null -> if (film.people != null) success(film)
            checkConnection() -> {
                val response = service.getFilmByIdAsync(id).await()
                if (response.isSuccessful) {
                    response.body().let { filmResponse ->
                        {
                            filmResponse?.let {
                                success(it)
                                filmDao.saveFilm(it)
                            }
                        }
                    }
                } else {
                    failed("Não foi possível obter o filme")
                }
            }
            else -> throw ConnectionException("Sem conexão com a internet")
        }
    }

}
