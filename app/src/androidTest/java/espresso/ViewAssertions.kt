package espresso

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion

fun recyclerViewShouldHaveItemCount(count: Int): ViewAssertion {
    return ViewAssertion { view, _ ->
        val recyclerView: RecyclerView = view as RecyclerView
        val actualCount: Int = recyclerView.adapter!!.itemCount
        if (actualCount != count) {
            throw AssertionError("RecyclerView has $actualCount while expected $count")
        }
    }
}