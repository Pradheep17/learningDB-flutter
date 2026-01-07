package com.pradheep.roomdb.database.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pradheep.roomdb.database.dao.NoteDAO
import com.pradheep.roomdb.model.response.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? =null

        fun getDatabase(context:Context):NoteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database").build()
                INSTANCE=instance
                instance
            }
        }
    }

}