package com.laomou.thumbnailator.tasks

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage


abstract class ThumbnailTask<S, D>(val param: ThumbnailParameter) {

    abstract fun read(): BufferedImage

    abstract fun write(img: BufferedImage)

    abstract fun getSource(): S

    abstract fun getDestination(): D
}