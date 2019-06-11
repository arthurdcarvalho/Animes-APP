package br.com.arthur.appanimes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.arthur.appanimes.model.People

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFilm(people: People)

    @Query("SELECT * FROM T_PEOPLE")
    fun getPeoples(): List<People>


}