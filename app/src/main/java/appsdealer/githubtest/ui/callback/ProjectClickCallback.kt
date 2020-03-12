package appsdealer.githubtest.ui.callback

import appsdealer.githubtest.api.model.Items

interface ProjectClickCallback {
    fun onClick(projects: Items)
}