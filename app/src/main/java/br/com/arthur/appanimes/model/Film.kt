package br.com.arthur.appanimes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "T_FILM")
class Film(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("producer")
    val producer: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("rt_score")
    val rtScore: String,

    @SerializedName("people")
    @TypeConverters(List::class)
    val people: List<String>?,

    @SerializedName("species")
    val species: List<String>?,

    @SerializedName("locations")
    val locations: List<String>?,

    @SerializedName("vehicles")
    val vehicles: List<String>?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("length")
    val length: Long?

)
