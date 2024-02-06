package dev.kuromiichi.examenrecumoviles.models

import dev.kuromiichi.examenrecumoviles.database.VotosDatabase
import kotlin.coroutines.coroutineContext

class Ciudad(
    val nombre: String,
) {
    var votosEmitidos: Int = 0
    var votosBlancos: Int = 0
    var votosNulos: Int = 0
}

