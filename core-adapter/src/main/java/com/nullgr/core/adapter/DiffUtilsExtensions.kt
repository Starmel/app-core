package com.nullgr.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nullgr.core.adapter.items.ListItem
import com.nullgr.core.adapter.items.ParentItem
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

inline fun calculate(
    oldItems: List<ListItem>,
    strategy: UpdateStrategy = UpdateStrategy.LATEST
): ObservableTransformer<List<ListItem>, Pair<List<ListItem>, DiffUtil.DiffResult>> {
    return ObservableTransformer { s ->
        if (strategy == UpdateStrategy.LATEST) s.switchMap { calculateObservable(oldItems, it) }
        else s.concatMap { calculateObservable(oldItems, it) }
    }
}

inline fun calculateObservable(
    oldItems: List<ListItem>,
    newItems: List<ListItem>
): Observable<Pair<List<ListItem>, DiffUtil.DiffResult>> =
    Observable.fromCallable { calculate(oldItems, newItems) }

inline fun calculate(
    oldItems: List<ListItem>,
    newItems: List<ListItem>
): Pair<List<ListItem>, DiffUtil.DiffResult> {
    val callback = Callback(ArrayList(oldItems), ArrayList(newItems))
    val result = DiffUtil.calculateDiff(callback, true)
    return Pair(newItems, result)
}

val DynamicAdapter.consumer: Consumer<Pair<List<ListItem>, DiffUtil.DiffResult>>
    get() = Consumer { pair ->
        setData(pair.first)
        pair.second.dispatchUpdatesTo(this)
    }

fun Observable<List<ListItem>>.bindTo(
    adapter: DynamicAdapter,
    compositeUnbind: CompositeDisposable,
    strategy: UpdateStrategy = UpdateStrategy.LATEST
) {
    this.observeOn(Schedulers.computation())
        .compose(calculate(adapter.items, strategy))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(adapter.consumer)
        .addTo(compositeUnbind)
}

fun <T : ListItem> List<T>.isChanged(other: List<T>): Boolean {
    if (size != other.size) {
        return true
    }

    forEachIndexed { index, item ->
        if (!item.areItemsTheSame(other[index])) {
            return true
        } else if (!item.areContentsTheSame(other[index])) {
            return true
        }
    }

    return false
}

fun ParentItem.isChanged(other: ParentItem): Boolean = this.items.isChanged(other.items)

/**
 * Represents strategy to choose how calculation results
 * should be delivered to [DynamicAdapter]
 */
enum class UpdateStrategy {
    /**
     * Only the last results will be delivered to [DynamicAdapter]
     */
    LATEST,

    /**
     * All results will be delivered to [DynamicAdapter] keeping the queue
     */
    SEQUENCE
}