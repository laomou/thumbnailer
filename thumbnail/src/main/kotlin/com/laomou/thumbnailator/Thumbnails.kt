package com.laomou.thumbnailator

import com.laomou.thumbnailator.tasks.SourceSinkThumbnailTask
import com.laomou.thumbnailator.tasks.io.BufferedImageSink
import com.laomou.thumbnailator.tasks.io.FileImageSource
import com.laomou.thumbnailator.tasks.io.ImageSource
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File


class Thumbnails {

    companion object {
        fun of(vararg files: String): Builder<File> {
            return Builder.ofStrings(files.asIterable())
        }

        fun of(vararg files: File): Builder<File> {
            return Builder.ofFiles(files.asIterable())
        }
    }

    class Builder<T>(val sources: Iterable<ImageSource<T>>) {
        var width = 0
        var height = 0

        fun size(width: Int, height: Int): Builder<T> {
            this.width = width
            this.height = height

            return this
        }

        fun makeParam(): ThumbnailParameter {
            return ThumbnailParameter(Dimension(width, height))
        }

        class StringImageSourceIterator(val filenames: Iterable<String>) : Iterable<ImageSource<File>> {
            override fun iterator(): MutableIterator<ImageSource<File>> {
                return object : MutableIterator<ImageSource<File>> {
                    var iter: Iterator<String> = filenames.iterator()

                    override fun hasNext(): Boolean {
                        return iter.hasNext()
                    }

                    override fun next(): ImageSource<File> {
                        return FileImageSource(iter.next())
                    }

                    override fun remove() {
                        throw UnsupportedOperationException()
                    }
                }
            }
        }

        class FileImageSourceIterator(val filenames: Iterable<File>) : Iterable<ImageSource<File>> {
            override fun iterator(): MutableIterator<ImageSource<File>> {
                return object : MutableIterator<ImageSource<File>> {
                    var iter: Iterator<File> = filenames.iterator()

                    override fun hasNext(): Boolean {
                        return iter.hasNext()
                    }

                    override fun next(): ImageSource<File> {
                        return FileImageSource(iter.next())
                    }

                    override fun remove() {
                        throw UnsupportedOperationException()
                    }
                }
            }
        }

        fun asBufferedImage(): BufferedImage {
            val iter = sources.iterator()
            val source = iter.next()

            require(!iter.hasNext()) { "Cannot create one thumbnail from multiple original images." }

            val destination = BufferedImageSink()

            Thumbnailator.createThumbnail(
                SourceSinkThumbnailTask<T, BufferedImage>(makeParam(), source, destination)
            )

            return destination.getSink()
        }

        fun asBufferedImages(): List<BufferedImage> {
            val thumbnails = ArrayList<BufferedImage>()

            for (source in sources) {
                val destination = BufferedImageSink()
                Thumbnailator.createThumbnail(
                    SourceSinkThumbnailTask<T, BufferedImage>(makeParam(), source, destination)
                )
                thumbnails.add(destination.getSink());
            }

            return thumbnails
        }


        companion object {
            fun ofStrings(fileNames: Iterable<String>): Builder<File> {
                val iter = StringImageSourceIterator(fileNames)
                return Builder(iter)
            }

            fun ofFiles(files: Iterable<File>): Builder<File> {
                val iter = FileImageSourceIterator(files)
                return Builder(iter)
            }
        }
    }
}
