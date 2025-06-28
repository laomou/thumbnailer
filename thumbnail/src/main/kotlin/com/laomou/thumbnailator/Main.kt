package com.laomou.thumbnailator

import java.awt.image.BufferedImage
import javax.imageio.ImageIO


fun aa() {
    var aa: BufferedImage? = null
    aa = Thumbnails.of("C:\\Users\\MM\\Downloads\\dump_more\\20250522213949\\algo_out.jpg").size(100, 100).asBufferedImage()
    aa.flush()
}

fun main() {
    val readers = ImageIO.getImageReadersByFormatName("JPEG")
    while (readers.hasNext()) {
        println("reader: " + readers.next())
    }
    ImageIO.setUseCache(false)
    aa()
    val a = 10
}