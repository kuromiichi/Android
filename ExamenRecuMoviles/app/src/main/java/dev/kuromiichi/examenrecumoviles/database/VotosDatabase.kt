package dev.kuromiichi.examenrecumoviles.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.kuromiichi.examenrecumoviles.models.Registro

@Database(entities = [Registro::class], version = 1)
abstract class VotosDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

    companion object {
        private const val DATABASE_NAME = "votos_database"
        private var INSTANCE: VotosDatabase? = null

        fun getInstance(context: Context): VotosDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    VotosDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE!!
        }
    }
}