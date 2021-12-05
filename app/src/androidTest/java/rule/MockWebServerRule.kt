package rule

import com.vnedomovnyi.randomusersmvi.network.ChangeableBaseUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule(private val testClassInstance: Any) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val needsMockWebServer = description.getAnnotation(
                    NeedsMockWebServer::class.java
                )
                if (needsMockWebServer != null) {
                    val mockWebServer = MockWebServer()
                    mockWebServer.start()

                    ChangeableBaseUrl.url = mockWebServer.url("").toString()

                    if (needsMockWebServer.setupMethods.isNotEmpty()) {
                        invokeSetupMethods(needsMockWebServer.setupMethods, mockWebServer)
                    }

                    try {
                        base.evaluate()
                    } finally {
                        mockWebServer.shutdown()
                    }
                } else {
                    base.evaluate()
                }
            }
        }
    }

    private fun invokeSetupMethods(setupMethodsArr: Array<String>, mockWebServer: MockWebServer) {
        for (setupMethodString in setupMethodsArr) {
            val setupMethod = testClassInstance
                .javaClass
                .getDeclaredMethod(
                    setupMethodString,
                    MockWebServer::class.java
                )

            setupMethod.invoke(testClassInstance, mockWebServer)
        }
    }
}