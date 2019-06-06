package br.com.arthur.appanimes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.arthur.appanimes.model.Film

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilm(film: Film)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilms(films: List<Film>)

    @Query("SELECT * FROM T_FILM F WHERE F.id = :id")
    fun getFilm(id: String): Film

    @Query("SELECT * FROM T_FILM")
    fun getFilms(): List<Film>

}