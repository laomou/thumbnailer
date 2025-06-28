package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage
import java.io.IOException

interface ImageSource<T> {
     fun setThumbnailParameter(parameter: ThumbnailParameter)
     @Throws(IOException::class)
     fun read(): BufferedImage
     fun getSource(): T
}