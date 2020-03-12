package appsdealer.githubtest.api.repository


import appsdealer.githubtest.api.model.Projects
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val HTTPS_API_GITHUB_URL = "https://api.github.com/"

class ApiClientRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(HTTPS_API_GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var githubService: GitHubApiService = retrofit.create(GitHubApiService::class.java)

    suspend fun getProjectListRe(userId: String): Response<Projects> =
        githubService.getProjectListRe(userId)

    companion object Factory {

        val instance: ApiClientRepository
            @Synchronized get() {
                return ApiClientRepository()
            }
    }
}