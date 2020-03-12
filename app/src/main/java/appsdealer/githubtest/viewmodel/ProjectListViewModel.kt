package appsdealer.githubtest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import appsdealer.githubtest.api.model.Projects
import appsdealer.githubtest.api.repository.ApiClientRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException


const val CREATED = "created:>"
const val LANGUAGE = " language:kotlin"

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApiClientRepository.instance
    var projectListLiveData: MutableLiveData<Pair<Projects?, Int>> = MutableLiveData()

    fun loadProjectList(date: String) {
        viewModelScope.launch {
            try {
                val request = repository.getProjectListRe(CREATED + date + LANGUAGE)
                if (request.isSuccessful) {
                    projectListLiveData.postValue(Pair(request.body(), 200))
                } else {
                    projectListLiveData.postValue(Pair(null, -1))
                }
            } catch (eU: UnknownHostException) {
                projectListLiveData.postValue(Pair(null, 0))
                eU.stackTrace
            } catch (e: Exception) {
                projectListLiveData.postValue(Pair(null, -1))
                e.stackTrace
            }
        }
    }
}