package com.hirocode.simplenews.di

import android.content.Context
import com.hirocode.simplenews.data.NewsRepository
import com.hirocode.simplenews.data.local.room.NewsDatabase
import com.hirocode.simplenews.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}