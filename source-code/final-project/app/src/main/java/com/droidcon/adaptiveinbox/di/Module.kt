package com.droidcon.adaptiveinbox.di

import com.droidcon.adaptiveinbox.data.DataRepository
import com.droidcon.adaptiveinbox.data.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepositoryImpl()
    }
}