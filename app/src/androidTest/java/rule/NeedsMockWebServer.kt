package rule

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NeedsMockWebServer(val setupMethods: Array<String> = [])
