package com.vnedomovnyi.randomusersmvi.ui.base

import io.reactivex.rxjava3.core.*

abstract class MviActionsProcessor<A : MviAction, R : MviResult> : ObservableTransformer<A, R> {

    final override fun apply(actions: Observable<A>): ObservableSource<R> {
        return actions.publish { shared ->
            Observable.merge(
                getActionProcessors(shared)
            )
        }
    }

    abstract fun getActionProcessors(shared: Observable<A>): List<Observable<R>>

    inline fun <reified A> Observable<in A>.connect(processor: ObservableTransformer<A, R>): Observable<R> {
        return ofType(A::class.java).compose(processor)
    }
}

fun <A : MviAction, R : MviResult> createUnitActionProcessor(
    schedulersProvider: SchedulersProvider? = null,
    initialResult: ((a: A) -> R?)? = null,
    onErrorResult: ((t: Throwable) -> R)? = null,
    doStuff: ObservableEmitter<R>.(action: A) -> Unit
) = ObservableTransformer<A, R> { actions ->
    actions
        .switchMap { action ->
            val observable = asObservable<R> {
                doStuff(action)
            }

            addObservableFeatures(
                observable,
                action,
                schedulersProvider,
                initialResult,
                onErrorResult,
            )
        }
}

fun <A : MviAction, R : MviResult> createObservableActionProcessor(
    schedulersProvider: SchedulersProvider? = null,
    initialResult: ((a: A) -> R?)? = null,
    onErrorResult: ((t: Throwable) -> R)? = null,
    observableArg: Observable<R>
) = ObservableTransformer<A, R> { actions ->
    actions
        .switchMap { action ->
            addObservableFeatures(
                observableArg,
                action,
                schedulersProvider,
                initialResult,
                onErrorResult
            )
        }
}

fun <A : MviAction, R : MviResult> addObservableFeatures(
    observableArg: Observable<R>,
    action: A,
    schedulersProvider: SchedulersProvider? = null,
    initialResult: ((a: A) -> R?)? = null,
    onErrorResult: ((t: Throwable) -> R)? = null,
): Observable<R> {
    var observable = observableArg

    schedulersProvider?.let {
        observable = observable
            .subscribeOn(schedulersProvider.subscriptionScheduler())
            .observeOn(schedulersProvider.observationScheduler())
    }

    if (onErrorResult != null) {
        observable = observable.onErrorReturn { t -> onErrorResult.invoke(t) }
    }

    if (initialResult != null) {
        observable =
            observable.startWith(Single.just(initialResult.invoke(action)))
    }

    return observable
}

fun <T> asObservable(doStuff: ObservableEmitter<T>.() -> Unit): Observable<T> =
    Observable.create<T> { emitter ->
        emitter.doStuff()
        emitter.onCompleteSafe()
    }

fun <T> ObservableEmitter<T>.onNextSafe(item: T) {
    if (!isDisposed) {
        onNext(item)
    }
}

fun <T> ObservableEmitter<T>.onCompleteSafe() {
    if (!isDisposed) {
        onComplete()
    }
}