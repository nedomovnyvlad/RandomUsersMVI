package espresso

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction

fun noOp() = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "no-op"

    override fun perform(uiController: UiController, view: View) {
        // no-op
    }
}