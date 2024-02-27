package dev.kuromiichi.examenfirebase.models

import com.google.firebase.firestore.Exclude

data class Event(
    @get:Exclude var id: String? = null,
    var familia: String? = null,
    var grupo: String? = null,
    var tipo: String? = null,
    var titulo: String? = null,
    var imgUrl: String? = null,
    var organismo: String? = null,
    var ponentes: String? = null,
    var hora: String? = null,
    var lugar: String? = null,
    var descripcion: String? = null
)