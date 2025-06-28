package com.laomou.thumbnailator.tasks.io

import java.io.IOException

class UnsupportedFormatException(override val message: String): IOException(message) {
}