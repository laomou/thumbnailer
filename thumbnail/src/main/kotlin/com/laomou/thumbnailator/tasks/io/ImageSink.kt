package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage

interface ImageSink<T> {
    fun setThumbnailParameter(parameter: ThumbnailParameter)
    fun write(img: BufferedImage)
    fun getSink(): T
}