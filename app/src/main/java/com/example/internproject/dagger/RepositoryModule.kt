package com.example.internproject.dagger

import com.example.internproject.repository.Repository
import com.example.internproject.repository.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repository: Repository): RepositoryInterface


}