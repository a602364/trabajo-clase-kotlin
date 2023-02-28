package net.azarquiel.caravanretrofit.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comunidad(
    var id: String,
    var nombre: String
) : Serializable

data class Provincia(
    var id: String,
    var comunidad: String,
    var nombre: String
) : Serializable

data class Municipio(
    var id: String,
    var nombre: String,
    var provincia: String,
    var latitud: String,
    var longitud: String,
) : Serializable

data class Lugar(
    var id: String,
    var titre: String,
    var description_es: String
) : Serializable

data class Foto(
    var id: String,
    var link_large: String,
    var link_thumb: String
) : Serializable

data class Usuario(
    var id: Int,
    var nick: String,
    var pass: String
) : Serializable

data class Respuesta(
    var comunidades: List<Comunidad>,
    var provincias: List<Provincia>,
    var municipios: List<Municipio>,
    @SerializedName("lieux") var lugares: List<Lugar>,
    @SerializedName("p4n_photos") var fotos: List<Foto>,
    val usuario: Usuario
)