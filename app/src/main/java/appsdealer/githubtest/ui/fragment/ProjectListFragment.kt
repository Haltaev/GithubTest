package appsdealer.githubtest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import appsdealer.githubtest.MainActivity
import appsdealer.githubtest.R
import appsdealer.githubtest.api.model.Items
import appsdealer.githubtest.common.PreferencesManager
import appsdealer.githubtest.databinding.FragmentProjectListBinding
import appsdealer.githubtest.ui.adapter.ProjectAdapter
import appsdealer.githubtest.ui.callback.ProjectClickCallback
import appsdealer.githubtest.viewmodel.ProjectListViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val DAY = 86400000L
private const val WEEK = 604800000L
private const val MONTH = 18144000000L

class ProjectListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ProjectListViewModel::class.java)
    }

    private lateinit var binding: FragmentProjectListBinding
    private lateinit var projectAdapter: ProjectAdapter
    private var preferencesManager: PreferencesManager? = null
    private lateinit var dateFormat: SimpleDateFormat

    private val projectClickCallback = object : ProjectClickCallback {
        override fun onClick(projects: Items) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                (activity as MainActivity).show(projects)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            preferencesManager = PreferencesManager(it)
        }

        dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)

        projectAdapter = ProjectAdapter(projectClickCallback)

        binding.apply {
            projectList.adapter = projectAdapter
            isLoading = true
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkLastChB()

        binding.apply {

            timeSort.setOnCheckedChangeListener { _, checkedId ->
                isLoading = true
                preferencesManager?.lastSelectedCheckbox = checkedId

                viewModel.loadProjectList(getDateByCheckedId(checkedId))
            }

            refreshButton.setOnClickListener {
                isLoading = true
                binding.errorLayout.visibility = GONE
                viewModel.loadProjectList(getDateByCheckedId(timeSort.checkedRadioButtonId))
            }

            viewModel.loadProjectList(getDateByCheckedId(timeSort.checkedRadioButtonId))
        }

        observeViewModel(viewModel)
    }

    private fun getDateByCheckedId(checkedId: Int): String {
        val now: Long = Calendar.getInstance().timeInMillis

        return when (checkedId) {
            R.id.day -> dateFormat.format(now - DAY)
            R.id.week -> dateFormat.format(now - WEEK)
            R.id.month -> dateFormat.format(now - MONTH)
            else -> ""
        }
    }

    private fun checkLastChB() {
        binding.apply {
            preferencesManager?.let { prefs ->
                val checkbox = when (prefs.lastSelectedCheckbox) {
                    R.id.day -> day
                    R.id.week -> week
                    R.id.month -> month
                    else -> week
                }

                checkbox.isChecked = true
            }
        }
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        viewModel.projectListLiveData.observe(viewLifecycleOwner, Observer { pair ->
            binding.apply {
                isLoading = false
                pair.first?.let { first ->
                    if (first.items.isNotEmpty()) {
                        recyclerLayout.visibility = VISIBLE
                        projectAdapter.setProjectList(first.items)
                    } else {
                        recyclerLayout.visibility = GONE
                        errorLayout.visibility = GONE
                    }
                } ?: kotlin.run {
                    recyclerLayout.visibility = GONE
                    errorLayout.visibility = VISIBLE
                    when (pair.second) {
                        0 -> {
                            errorTv.setText(R.string.error_no_internet)
                        }
                        -1 -> {
                            errorTv.setText(R.string.error_unknown)
                        }
                    }
                }
            }
        })
    }
}