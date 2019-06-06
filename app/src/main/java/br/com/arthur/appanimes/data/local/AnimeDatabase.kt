package br.com.arthur.appanimes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.arthur.appanimes.model.Film

@Database(entities = [Film::class], exportSchema = true, version = 1)
@TypeConverters(Converters::class)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun getFilmDao(): FilmDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getDatabase(context: Context): AnimeDatabase {
            val bankInstance = INSTANCE
            if (bankInstance != null) {
                return bankInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "Anime_Database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}