package com.laomou.thumbnailator.tasks

import com.laomou.thumbnailator.ThumbnailParameter
import com.laomou.thumbnailator.tasks.io.ImageSink
import com.laomou.thumbnailator.tasks.io.ImageSource
import java.awt.image.BufferedImage
import java.io.IOException

class SourceSinkThumbnailTask<S, D>(
    param: ThumbnailParameter,
    val source: ImageSource<S>,
    val destination: ImageSink<D>
) :
    ThumbnailTask<S, D>(param) {

    init {
        source.setThumbnailParameter(param)
        destination.setThumbnailParameter(param)
    }

    @Throws(IOException::class)
    override fun read(): BufferedImage {
        val img = source.read()
        return img
    }

    override fun write(img: BufferedImage) {
        destination.write(img)
    }

    override fun getSource(): S {
        return source.getSource()
    }

    override fun getDestination(): D {
        return destination.getSink()
    }
}