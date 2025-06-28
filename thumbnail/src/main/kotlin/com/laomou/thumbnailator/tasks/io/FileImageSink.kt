package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class FileImageSink(val destinationFile: File) : ImageSink<File> {
    private val param: ThumbnailParameter? = null

    constructor(destinationFilePath: String): this(File(destinationFilePath))

    private var imageSink: ImageSink<*> = UninitializedImageSink()

    override fun setThumbnailParameter(parameter: ThumbnailParameter) {
        this.imageSink.setThumbnailParameter(parameter)
    }

    override fun write(img: BufferedImage) {
        imageSink.write(img)

        val os: OutputStream = FileOutputStream(destinationFile)
        imageSink = OutputStreamImageSink(os)
        imageSink.setThumbnailParameter(param!!)
        try {
            imageSink.write(img)
        } finally {
            os.close()
        }
    }

    override fun getSink(): File {
        return destinationFile
    }



    class UninitializedImageSink : AbstractImageSink<Void>() {
        override fun getSink(): Void {
            throw IllegalStateException("This should not happen.")
        }
    }

}