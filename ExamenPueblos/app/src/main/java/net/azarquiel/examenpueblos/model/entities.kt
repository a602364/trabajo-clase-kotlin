package net.azarquiel.examenpueblos.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "comunidad")
data class Comunidad(
    @PrimaryKey
    var id: Int = 0,
    var nombre: String = ""
) : Serializable

@Entity(
    tableName = "provincia", foreignKeys = [ForeignKey(
        entity = Comunidad::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("comunidad")
    )]
)
data class Provincia(
    @PrimaryKey
    var id: Int = 0,
    var nombre: String = "",
    var comunidad: Int = 0
) : Serializable

data class ProvinciaView(
    @PrimaryKey
    var id: Int = 0,
    var nombre: String = "",
    var comunidad: Int = 0,
    var nombreComunidad: String = ""
) : Serializable

@Entity(
    tableName = "pueblo", foreignKeys = [ForeignKey(
        entity = Provincia::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("provincia")
    )]
)
data class Pueblo(
    @PrimaryKey
    var id: Int = 0,
    var nombre: String = "",
    var imagen: String = "",
    var provincia: Int = 0,
    var link: String = "",
    var fav: Int = 0
) : Serializable

data class PuebloView(
    @PrimaryKey
    var id: Int = 0,
    var nombre: String = "",
    var imagen: String = "",
    var provincia: Int = 0,
    var link: String = "",
    var fav: Int = 0,
    var nombreProvincia: String = ""
) : Serializable



