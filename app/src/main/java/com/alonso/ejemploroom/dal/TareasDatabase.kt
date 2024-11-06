package com.alonso.ejemploroom.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alonso.ejemploroom.ent.TareaEntity

@Database(entities=[TareaEntity::class], version=1, exportSchema=true)
abstract class TareasDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDAO
}
