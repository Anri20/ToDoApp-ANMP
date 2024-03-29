package com.example.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        //        Volatile means that writes to this field are immediately made visible to other threads
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
//            databaseBuilder requires context, database class, and database name as String
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "newtododb"
            ).build()

        operator fun invoke(context: Context) {
//            Singleton restrict the instantiation of a class to one "single" instance
            if (instance != null) {
//                a thread that enters synchornized method obtains a lock
//                (an object being locked is the instance of the containing class)
//                and no other thread can enter the method until the lock is released
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}