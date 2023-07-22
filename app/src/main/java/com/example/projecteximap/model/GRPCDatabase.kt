package com.example.projecteximap.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PostDetails::class, GRPCRemoteKeys::class], version = 1)
@TypeConverters(MediaUrlConverter::class)
abstract class GRPCDatabase : RoomDatabase() {
    abstract fun postDetailsDao(): PostDetailsDAO
    abstract fun remoteKeysDao(): RemoteKeysDAO

    companion object {
        @Volatile
        var DATABASE: GRPCDatabase? = null

        fun getDatabase(context: Context): GRPCDatabase {
            if (DATABASE == null) {
                synchronized(this) {
                    DATABASE = Room.databaseBuilder(
                        context.applicationContext,
                        GRPCDatabase::class.java,
                        "articleDB"
                    ).build()
                }
            }
            return DATABASE!!
        }
    }
}