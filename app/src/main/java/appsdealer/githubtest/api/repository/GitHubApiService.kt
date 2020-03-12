package appsdealer.githubtest.api.repository

import appsdealer.githubtest.api.model.Projects
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/repositories")
    suspend fun getProjectListRe(
        @Query("q") date: String,
        @Query("sort") sort: String = "stars"
    ): Response<Projects>
}