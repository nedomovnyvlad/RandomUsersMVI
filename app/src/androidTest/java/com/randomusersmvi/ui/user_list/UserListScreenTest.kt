package com.randomusersmvi.ui.user_list

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.vnedomovnyi.randomusersmvi.MainActivity
import com.vnedomovnyi.randomusersmvi.R
import com.vnedomovnyi.randomusersmvi.db.UserDao
import espresso.ToastMatcher
import espresso.noOp
import espresso.recyclerViewShouldHaveItemCount
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import rule.MockWebServerRule
import rule.NeedsMockWebServer

@RunWith(AndroidJUnit4::class)
class UserListScreenTest : KoinTest {

    val userDao: UserDao by inject()

    @get:Rule
    val rules: TestRule = MockWebServerRule(this)

    @get:Rule
    var tasksActivityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                userDao.delete()
            }
        }

    @Test
    @NeedsMockWebServer(setupMethods = ["enqueueSuccessfulResponse"])
    fun shouldDisplayAllItemsFromSuccessfulResponse() {
        onView(withId(R.id.recyclerView)).check(recyclerViewShouldHaveItemCount(USER_COUNT))

        for (i in 0 until USER_COUNT) {
            performActionOnItemWithText(FULL_USER_NAMES[i], noOp())
        }
    }

    @Test
    @NeedsMockWebServer(setupMethods = ["enqueueSuccessfulResponse"])
    fun shouldDeleteUserOnClick() {
        onView(withId(R.id.recyclerView)).check(recyclerViewShouldHaveItemCount(USER_COUNT))

        performActionOnItemWithText(FULL_USER_NAMES[0], click())

        onView(withId(R.id.recyclerView)).check(recyclerViewShouldHaveItemCount(USER_COUNT - 1))
    }

    @Test
    @NeedsMockWebServer(setupMethods = ["enqueueSuccessfulResponse"])
    fun shouldShowDeleteMessageOnClick() {
        onView(withId(R.id.recyclerView)).check(recyclerViewShouldHaveItemCount(USER_COUNT))

        performActionOnItemWithText(FULL_USER_NAMES[0], click())

        onView(withText(getResourceString(R.string.delete_success_message))).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        waitForToastToDisappear()
    }

    private fun performActionOnItemWithText(text: String, action: ViewAction) {
        onView(withId(R.id.recyclerView))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(
                        allOf(
                            withId(R.id.name),
                            withText(text)
                        )
                    ),
                    action
                )
            )
    }

    @Test
    @NeedsMockWebServer(setupMethods = ["enqueueUnsuccessfulResponse"])
    fun shouldShowErrorMessageOnRequestFail() {
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))

        onView(withText(getResourceString(R.string.error_message))).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        waitForToastToDisappear()
    }

    private fun getResourceString(id: Int): String {
        val targetContext = InstrumentationRegistry.getTargetContext()
        return targetContext.resources.getString(id)
    }

    private fun waitForToastToDisappear() {
        SystemClock.sleep(TOAST_SHORT_DELAY)
    }

    fun enqueueSuccessfulResponse(mockWebServer: MockWebServer) {
        mockWebServer.enqueue(MockResponse().setBody(RESPONSE_JSON))
    }

    fun enqueueUnsuccessfulResponse(mockWebServer: MockWebServer) {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
    }

    companion object {
        const val USER_COUNT = 2
        private val USER_NAMES = arrayOf("User1", "User2")
        val FULL_USER_NAMES = USER_NAMES.map { "$it " }
        val RESPONSE_JSON =
            """{"results":[{"gender":"male","name":{"title":"Mr","first":"${USER_NAMES[0]}","last":""},"location":{"street":{"number":1677,"name":"Sunset St"},"city":"Seattle","state":"Maryland","country":"United States","postcode":22716,"coordinates":{"latitude":"-27.8882","longitude":"-20.6851"},"timezone":{"offset":"-10:00","description":"Hawaii"}},"email":"vernon.kim@example.com","login":{"uuid":"f712cd62-1570-4d8e-87f4-d5b771793729","username":"yellowfish879","password":"inter","salt":"hwDaYF5V","md5":"184f43ec9e5f75f4641d0f92b436290d","sha1":"fd7822c90580b20cc249a052555f3291e02b5e77","sha256":"8c50dc8c4c5bde81ffb8644e046849a9ecde971be2766f616038b90353411442"},"dob":{"date":"1962-05-29T15:06:13.820Z","age":59},"registered":{"date":"2015-03-05T04:05:12.714Z","age":6},"phone":"(404)-514-1556","cell":"(511)-551-4314","id":{"name":"SSN","value":"751-17-5793"},"picture":{"large":"https://randomuser.me/api/portraits/men/49.jpg","medium":"https://randomuser.me/api/portraits/med/men/49.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/men/49.jpg"},"nat":"US"},{"gender":"female","name":{"title":"Ms","first":"${USER_NAMES[1]}","last":""},"location":{"street":{"number":4448,"name":"James St"},"city":"Tampa","state":"Delaware","country":"United States","postcode":82935,"coordinates":{"latitude":"14.4107","longitude":"49.4293"},"timezone":{"offset":"-4:00","description":"Atlantic Time (Canada), Caracas, La Paz"}},"email":"melissa.jimenez@example.com","login":{"uuid":"92299275-27b9-412b-8bf4-6cb7e476e2cc","username":"ticklishbird603","password":"kitkat","salt":"V7RYbMFZ","md5":"4e488a6c9aec20f64ff588a646ee6370","sha1":"b871110764bc7fe6a78a2fb8ab9dd37d68a6ba30","sha256":"35cacffd5dc9891ff4fbf96f24f0159f133b307756be028c3d2c0eac190744b1"},"dob":{"date":"1985-04-12T11:30:35.813Z","age":36},"registered":{"date":"2003-11-28T08:16:24.794Z","age":18},"phone":"(865)-715-1254","cell":"(296)-448-7528","id":{"name":"SSN","value":"894-83-1087"},"picture":{"large":"https://randomuser.me/api/portraits/women/60.jpg","medium":"https://randomuser.me/api/portraits/med/women/60.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/60.jpg"},"nat":"US"}],"info":{"seed":"d90ed8655f90eba1","results":10,"page":1,"version":"1.3"}}"""

        const val TOAST_SHORT_DELAY = 2000L
    }

}