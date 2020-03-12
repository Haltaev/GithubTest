package appsdealer.githubtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import appsdealer.githubtest.api.model.Items
import appsdealer.githubtest.ui.fragment.ProjectFragment
import appsdealer.githubtest.ui.fragment.ProjectListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun show(project: Items) {

        val projectFragment = ProjectFragment.forProject(project.owner.login + "/" + project.name)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack("project")
            .add(R.id.fragment_container, projectFragment, null)
            .commit()
    }
}
