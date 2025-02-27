package com.example.taskreminder.core.di

import android.content.Context
import androidx.room.Room
import com.example.taskreminder.data.db.AppDatabase
import com.example.taskreminder.data.db.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "events_db"
        ).build()

    @Provides
    fun provideDao(appDatabase: AppDatabase): EventDao = appDatabase.eventDao()
}