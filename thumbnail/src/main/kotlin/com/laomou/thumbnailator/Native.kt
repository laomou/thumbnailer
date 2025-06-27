package com.laomou.thumbnailator

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

data class ImageThumbnail(
    val rgbaData: ByteArray,
    val width: Int,
    val height: Int
)

object Native {
    init {
        loadLibrary("thumbnail")
    }

    private fun loadLibrary(baseName: String) {
        val os = System.getProperty("os.name").lowercase()
        val platform = when {
            os.contains("win") -> "windows"
            os.contains("mac") -> "macos"
            os.contains("nux") -> "linux"
            else -> throw UnsupportedOperationException("Unsupported OS: $os")
        }

        val fileName = when (platform) {
            "windows" -> "$baseName.dll"
            "macos"   -> "lib$baseName.dylib"
            "linux"   -> "lib$baseName.so"
            else      -> error("Unexpected platform")
        }

        val jarPath = "/META-INF/lib/$platform/$fileName"
        val resource: InputStream = Native::class.java.getResourceAsStream(jarPath)
            ?: throw IllegalStateException("Native library not found in JAR: $jarPath")

        val temp = File.createTempFile(baseName, null).apply {
            deleteOnExit()
            Files.copy(resource, toPath(), StandardCopyOption.REPLACE_EXISTING)
        }

        System.load(temp.absolutePath)
    }

    external fun generateThumbnail(
        inputPath: String,
        maxWidth: Int,
        maxHeight: Int
    ): ImageThumbnail
}