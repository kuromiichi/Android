package dev.kuromiichi.examenrecumoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.kuromiichi.examenrecumoviles.models.Registro

@Dao
interface RegistroDao{
    @Query("SELECT * FROM registro")
    fun findAll(): List<Registro>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(ciudad: Registro)
}