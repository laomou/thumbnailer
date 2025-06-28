package com.laomou.thumbnailator.tasks.io

import java.awt.image.BufferedImage
import java.io.IOException
import java.io.OutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO


class OutputStreamImageSink(val outstream: OutputStream) : AbstractImageSink<OutputStream>() {

    override fun write(img: BufferedImage) {
        super.write(img)

        val formatName = "jpg"
        val writers = ImageIO.getImageWritersByFormatName(formatName)
        if (!writers.hasNext()) {
            throw IOException(
                "No suitable ImageWriter found for $formatName."
            )
        }

        val writer = writers.next()
        val writeParam = writer.defaultWriteParam

        val ios = ImageIO.createImageOutputStream(outstream)
        if (ios == null) {
            throw IOException("Could not open OutputStream.")
        }

        writer.setOutput(ios)
        writer.write(null, IIOImage(img, null, null), writeParam)

        writer.dispose()
        ios.close()
    }

    override fun getSink(): OutputStream {
        return outstream
    }
}