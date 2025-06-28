package com.laomou.thumbnailator.tasks.io

import java.awt.image.BufferedImage

class BufferedImageSink : AbstractImageSink<BufferedImage>() {
     private var img: BufferedImage? = null
     private var written = false

     override fun write(img: BufferedImage) {
          this.img = img
          written = true
     }

     override fun getSink(): BufferedImage {
          if (!written) {
               throw IllegalStateException("BufferedImageSink has not been written to yet.");
          }
          return img!!
     }


}