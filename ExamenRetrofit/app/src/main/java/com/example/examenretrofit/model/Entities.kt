package com.example.examenretrofit.model

import java.io.Serializable

data class Chiste(
    var id: Int,
    var idcategoria: Int,
    var contenido: String ,
    ): Serializable

data class Categoria(
    var id: Long,
    var nombre: String
): Serializable

data class Punto(
    var id:Long,
    var idchiste:Long,
    var puntos: Int)

data class Usuario (
    var id: Int,
    var nick: String,
    var pass: String
    ): Serializable

data class Respuesta (
    var chistes: List<Chiste>,
    var categorias: List<Categoria>,
    val chiste: Chiste,
    val usuario: Usuario,
    val puntos:List<Punto>,
    val punto: Punto,
    val avg: Int,
    val msg: String
)