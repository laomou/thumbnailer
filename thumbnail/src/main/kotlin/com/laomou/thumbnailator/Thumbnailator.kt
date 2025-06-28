package com.laomou.thumbnailator

import com.laomou.thumbnailator.tasks.ThumbnailTask
import com.twelvemonkeys.image.ResampleOp
import java.awt.image.BufferedImageOp


class Thumbnailator {
    companion object {
        fun createThumbnail(task: ThumbnailTask<*, *>) {
            val param: ThumbnailParameter = task.param
            val sourceImage = task.read()

            val resampler: BufferedImageOp = ResampleOp(
                param.thumbnailSize.width,
                param.thumbnailSize.height,
                ResampleOp.FILTER_LANCZOS)
            val destinationImage = resampler.filter(sourceImage, null)

            task.write(destinationImage)

            sourceImage.flush()
            sourceImage.graphics.dispose()

            destinationImage.flush()
            destinationImage.graphics.dispose()
        }
    }
}