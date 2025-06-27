package com.laomou.thumbnailator

import java.awt.image.BufferedImage
import java.io.File
import javax.swing.Icon
import javax.swing.ImageIcon

class Thumbnails private constructor(private val file: File) {
    private var width = 100
    private var height = 100
    private var keepAspect = true

    companion object {
        fun of(file: File): Thumbnails = Thumbnails(file)
    }

    fun size(w: Int, h: Int) = apply {
        width = w
        height = h
    }

    fun keepAspectRatio(enable: Boolean) = apply {
        keepAspect = enable
    }

    fun toBufferedImage(): BufferedImage {
        return Native.generateThumbnail(file.path, width, height).toBufferedImage()
    }

    fun toIcon(): Icon {
        return ImageIcon(toBufferedImage())
    }
}

fun ImageThumbnail.toBufferedImage(): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val offset = (y * width + x) * 4
            val argb = (
                    (rgbaData[offset + 3].toInt() and 0xFF shl 24) or
                            (rgbaData[offset].toInt() and 0xFF shl 16) or
                            (rgbaData[offset + 1].toInt() and 0xFF shl 8) or
                            (rgbaData[offset + 2].toInt() and 0xFF)
                    )
            image.setRGB(x, y, argb)
        }
    }

    return image
}
