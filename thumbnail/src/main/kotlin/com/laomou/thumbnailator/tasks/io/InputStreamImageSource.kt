package com.laomou.thumbnailator.tasks.io

import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import javax.imageio.ImageIO


class InputStreamImageSource(val instream: InputStream) : AbstractImageSource<InputStream>() {

    @Throws(IOException::class)
    override fun read(): BufferedImage  {
        val iis = ImageIO.createImageInputStream(instream)
        if (iis == null) {
            throw IOException("Could not open InputStream.")
        }

        val readers = ImageIO.getImageReaders(iis)
        if (!readers.hasNext()) {
            iis.close()
            throw UnsupportedFormatException(
                "No suitable ImageReader found for source data."
            )
        }

        val reader = readers.next()
        reader.setInput(iis, true, true)

        var isExceptionThrown = false

        try {
            val irParam = reader.defaultReadParam
            val img = reader.read(0, irParam)
            return finishedReading(img)
        } catch (e: IOException) {
            isExceptionThrown = true
            throw e
        } finally {
            reader.dispose()

            try {
                iis.close()
            } catch (e: IOException) {
                if (!isExceptionThrown) {
                    throw e
                }
            }
        }
    }

    override fun getSource(): InputStream {
        return instream
    }
}