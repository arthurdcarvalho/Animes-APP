package br.com.arthur.appanimes.data.repository

import br.com.arthur.appanimes.data.local.FilmDao
import br.com.arthur.appanimes.data.remote.ServiceFactory
import br.com.arthur.appanimes.model.Film

class FilmRepository(private val filmDao: FilmDao) {

    private val service = ServiceFactory.getService()

    suspend fun getFilms(success: (List<Film>) -> Unit) {
        val response = service.getFilmsAsync().await()

        if (response.isSuccessful) {
            response.body().let { films ->
                run {
                    films?.forEach { film ->
                        run {
                            filmDao.saveFilm(film)
                            success(films)
                        }

                    }
                }
            }
        } else {
            success(filmDao.getFilms())
        }
    }

    suspend fun getFilmById(id: String, success: (Film) -> Unit) {
        val response = service.getFilmById(id).await()
        val film = filmDao.getFilm(id)

        if (response.isSuccessful) {
            response.body().let { filmResponse ->
                filmResponse?.let {
                    success(it)
                    filmDao.saveFilm(it)
                }
            }
        } else if (film.people != null) {
            success(film)
        }
    }
}
