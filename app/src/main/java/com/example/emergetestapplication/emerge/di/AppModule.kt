package com.example.emergetestapplication.emerge.di

import android.content.Context
import androidx.room.Room
import com.example.emergetestapplication.emerge.AppDatabase
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSource
import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSourceImpl
import com.example.emergetestapplication.emerge.data.datasource.remote.MoviesRemoteDataSource
import com.example.emergetestapplication.emerge.data.datasource.remote.MoviesRemoteDataSourceImpl
import com.example.emergetestapplication.emerge.data.model.db.UserDao
import com.example.emergetestapplication.emerge.data.network.APIService
import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepositoryImpl
import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository
import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepositoryImpl
import com.example.emergetestapplication.emerge.domain.usecase.GetPopularMoviesUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "app_database",
            ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: UserDao): AuthLocalDataSource = AuthLocalDataSourceImpl(userDao)

    @Provides
    @Singleton
    fun provideAuthRepository(localDataSource: AuthLocalDataSource): AuthRepository = AuthRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: APIService): MoviesRemoteDataSource = MoviesRemoteDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: MoviesRemoteDataSource): MovieRepository = MovieRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
}
