package com.laomou.thumbnailator.tasks.io

import java.awt.image.BufferedImage

class BufferedImageSource(val img: BufferedImage): AbstractImageSource<BufferedImage>() {

    override fun read(): BufferedImage {
        return finishedReading(img)
    }

    override fun getSource(): BufferedImage {
        return img
    }
}