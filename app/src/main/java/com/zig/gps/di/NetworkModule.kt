package com.zig.gps.di

import com.zig.gps.api.ApiService
import com.zig.gps.utils.Constants.API_VERSION_KEY
import com.zig.gps.utils.Constants.API_VERSION_VALUE
import com.zig.gps.utils.Constants.BASE_URL
import com.zig.gps.utils.Constants.BASE_URL_AGENCY_KEY
import com.zig.gps.utils.Constants.BASE_URL_AGENCY_VALUE
import com.zig.gps.utils.Constants.BASE_URL_ECOLANE
import com.zig.gps.utils.Constants.TOKEN_KEY
import com.zig.gps.utils.Constants.TOKEN_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesUrl() =  BASE_URL

    @Singleton
    @Provides
    fun provideHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            /*.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(TOKEN_KEY, TOKEN_VALUE)
                    .addHeader(BASE_URL_AGENCY_KEY, BASE_URL_AGENCY_VALUE)
                    .addHeader(API_VERSION_KEY, API_VERSION_VALUE)
                    .build()
                chain.proceed(newRequest)
            }*/
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(120L, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesRetrofit(
        url:String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun providesTopHeadlinesApi(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java)
}
