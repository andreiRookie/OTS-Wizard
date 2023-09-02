package ru.otus.basicarchitecture.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.otus.basicarchitecture.domain.GetAddressSuggestionUseCase
import ru.otus.basicarchitecture.network.AuthInterceptor
import ru.otus.basicarchitecture.network.DaDataRepository
import ru.otus.basicarchitecture.network.DaDataService
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityRetainedComponent::class)
class HiltNetworkModule {

    companion object {
        private const val BASE_URL = "https://suggestions.dadata.ru/"
    }

    @Reusable
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Reusable
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideService(retroFit: Retrofit): DaDataService {
        return retroFit.create(DaDataService::class.java)
    }


    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideGetSuggestionAddressUseCase(repository: DaDataRepository): GetAddressSuggestionUseCase {
        return GetAddressSuggestionUseCase(repository)
    }

}