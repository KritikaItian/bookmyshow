package com.krutika.bookmyshow.data.module

import com.krutika.bookmyshow.data.repository.showTimeRepository
import com.krutika.bookmyshow.data.repository.showTimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {


    @Binds
    abstract fun bindRepository(impl: showTimeRepositoryImpl): showTimeRepository

}