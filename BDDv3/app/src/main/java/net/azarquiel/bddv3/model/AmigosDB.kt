package net.azarquiel.bddv3.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Grupo::class, Amigo::class], version = 1)
abstract class AmigosDB : RoomDatabase(){
    abstract fun grupoDao(): GrupoDao
    abstract fun amigoDao(): AmigoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE:  AmigosDB? = null

        fun getDatabase(context: Context): AmigosDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AmigosDB::class.java,   "amigosDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}