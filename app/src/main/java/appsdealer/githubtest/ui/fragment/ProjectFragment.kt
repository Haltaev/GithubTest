package appsdealer.githubtest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import appsdealer.githubtest.R
import appsdealer.githubtest.databinding.FragmentProjectDetailsBinding


private const val PROJECT_URL = "PROJECT_URL"
private const val GIT_URL = "https://github.com/"

class ProjectFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val projectUrl = GIT_URL + arguments?.getString(PROJECT_URL)

        binding.apply {
            binding.webView.loadUrl(projectUrl)
            isLoading = true
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress == 100) {
                    binding.isLoading = false

                }
            }
        }
    }

    companion object {
        fun forProject(projectUrl: String): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()

            args.putString(PROJECT_URL, projectUrl)
            fragment.arguments = args

            return fragment
        }
    }
}