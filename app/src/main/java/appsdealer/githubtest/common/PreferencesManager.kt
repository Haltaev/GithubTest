package appsdealer.githubtest.common


import android.content.Context
import appsdealer.githubtest.R

class PreferencesManager(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    var lastSelectedCheckbox: Int
        get() {
            return pref.getInt(LAST_SELECTED_CHECKBOX, R.id.week)
        }
        set(checkBoxId) {
            editor.putInt(LAST_SELECTED_CHECKBOX, checkBoxId)
            editor.commit()
        }

    companion object {
        const val PREF_NAME = "AppsDealerPrefs"

        private const val LAST_SELECTED_CHECKBOX = "LAST_SELECTED_CHECKBOX"
    }
}
