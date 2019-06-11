package br.com.arthur.appanimes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "T_PEOPLE")
class People(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("age")
    @ColumnInfo(name = "age")
    val age: String?,

    @SerializedName("gender")
    @ColumnInfo(name = "gender")
    val gender: String?,

    @SerializedName("eye_color")
    @ColumnInfo(name = "eye_color")
    val eyeColor: String?,

    @SerializedName("hair_color")
    @ColumnInfo(name = "hair_color")
    val hairColor: String?,

    @SerializedName("films")
    @ColumnInfo(name = "films")
    @TypeConverters(List::class)
    val films: List<String>?,

    @SerializedName("species")
    @ColumnInfo(name = "species")
    val species: String?,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String?,

    @SerializedName("length")
    @ColumnInfo(name = "length")
    val length: Long?

)