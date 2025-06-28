package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage


abstract class AbstractImageSink<T>: ImageSink<T> {
    protected var param: ThumbnailParameter? = null

    override fun setThumbnailParameter(parameter: ThumbnailParameter) {
        this.param = parameter
    }

    override fun write(img: BufferedImage) {
    }
}