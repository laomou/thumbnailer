package com.laomou.thumbnailator.tasks.io

import com.laomou.thumbnailator.ThumbnailParameter
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


class FileImageSource(val sourceFile: File) : ImageSource<File> {
    private var params: ThumbnailParameter? = null
    private var imageSource: ImageSource<*> = UninitializedImageSource()

    constructor(sourceFilePath: String) : this(File(sourceFilePath))

    override fun setThumbnailParameter(parameter: ThumbnailParameter) {
        this.imageSource.setThumbnailParameter(parameter)
        this.params = parameter
    }

    @Throws(IOException::class)
    override fun read(): BufferedImage {
        val fis: FileInputStream?
        try {
            fis = FileInputStream(sourceFile)
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException(
                "Could not find file: " + sourceFile.absolutePath
            )
        }

        try {
            imageSource = InputStreamImageSource(fis)
            imageSource.setThumbnailParameter(requireNotNull(this.params))
            return imageSource.read()
        } catch (e: UnsupportedFormatException) {
            throw IOException("No suitable ImageReader found for ${sourceFile.absoluteFile}.")
        } finally {
            fis.close()
        }
    }

    override fun getSource(): File {
        return sourceFile
    }

    class UninitializedImageSource : AbstractImageSource<Void?>() {
        override fun read(): BufferedImage {
            throw IllegalStateException("This should not happen.")
        }

        override fun getSource(): Void? {
            throw IllegalStateException("This should not happen.")
        }
    }
}