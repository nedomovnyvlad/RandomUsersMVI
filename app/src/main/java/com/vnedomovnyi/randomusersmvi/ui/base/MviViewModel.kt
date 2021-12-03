package com.vnedomovnyi.randomusersmvi.ui.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable

@Suppress("UNCHECKED_CAST")
abstract class MviViewModel<A : MviAction, R : MviResult, VS : MviViewState<R>>(
    private val savedStateHandle: SavedStateHandle,
    actionProcessor: ObservableTransformer<A, R>,
    defaultViewState: VS
) : ViewModel() {

    protected var viewState: VS = savedStateHandle.get<VS>(VIEW_STATE_KEY) ?: defaultViewState

    private val actionsObserver = PublishRelay.create<A>()
    private val actionsSource = PublishRelay.create<A>()
    private val disposable = actionsSource.subscribe(actionsObserver)

    val viewStatesObservable: Observable<VS> by lazy {
        actionsObserver
            .compose(actionProcessor)
            .scan(viewState, ::reduce)
            .distinctUntilChanged()
            .doOnNext(::save)
            .replay(1)
            .autoConnect(0)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun init(render: (VS) -> Unit): Disposable = viewStatesObservable.subscribe(render)

    fun accept(action: A) = actionsSource.accept(action)

    private fun reduce(viewState: VS, result: R) = viewState.reduce(result) as VS

    private fun save(newViewState: VS) {
        if (newViewState.isSavable()) {
            savedStateHandle.set(VIEW_STATE_KEY, onSaveViewState(newViewState))
            viewState = newViewState
        }
    }

    /**
     * Transform viewState when saving it to the savedStateHandle.
     * It will then be restored after after view model recreation.
     */
    open fun onSaveViewState(viewState: VS): VS? = viewState

    companion object {
        private const val VIEW_STATE_KEY = "ViewStateKey"
    }
}
