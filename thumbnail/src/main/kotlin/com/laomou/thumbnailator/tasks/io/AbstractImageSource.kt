package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter

abstract class AbstractImageSource<T> : ImageSource<T> {
    var param: ThumbnailParameter? = null
    var hasReadInput: Boolean = false

    protected fun <V> finishedReading(returnValue: V): V {
        hasReadInput = true
        return returnValue
    }

    override fun setThumbnailParameter(parameter: ThumbnailParameter) {
        this.param = parameter
    }
}