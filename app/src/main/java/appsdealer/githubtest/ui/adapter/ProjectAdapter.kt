package appsdealer.githubtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import appsdealer.githubtest.R
import appsdealer.githubtest.api.model.Items
import appsdealer.githubtest.databinding.ProjectListItemBinding
import appsdealer.githubtest.ui.callback.ProjectClickCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectAdapter(private val projectClickCallback: ProjectClickCallback?) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    private var projectList: List<Items>? = null

    fun setProjectList(projectList: List<Items>) {
        this.projectList = projectList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ProjectViewHolder {
        val binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.project_list_item, parent,
                false
            ) as ProjectListItemBinding

        binding.callback = projectClickCallback

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        projectList?.let { items ->
            holder.binding.project = items.get(position)
            holder.binding.executePendingBindings()
            Picasso.get().load(items[position].owner.avatar_url.toUri())
                .into(holder.itemView.ownerProfilePhoto)
            if (items[position].description.isNullOrEmpty()) {
                holder.itemView.description.visibility = View.GONE
            } else {
                holder.itemView.description.text = items[position].description
            }
        }
    }

    override fun getItemCount(): Int {
        return projectList?.size ?: 0
    }

    open class ProjectViewHolder(val binding: ProjectListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}