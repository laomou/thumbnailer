import org.gradle.internal.os.OperatingSystem

tasks.register("buildRust") {
    group = "build"
    doLast {
        exec {
            workingDir = file("src")
            commandLine = listOf("cargo", "build", "--release")
        }

        val os = OperatingSystem.current()
        val libName = when {
            os.isWindows -> "thumbnail_lib.dll"
            os.isMacOsX -> "libthumbnail_lib.dylib"
            else -> "libthumbnail_lib.so"
        }

        val outputLib = file("target/release/$libName")
        val destDir = file("../thumbnail/src/main/libs")

        if (outputLib.exists()) {
            destDir.mkdirs()
            outputLib.copyTo(destDir.resolve(libName), overwrite = true)
            println("✔ Rust 库已拷贝到资源目录: ${destDir.absolutePath}")
        } else {
            println("⚠ 未找到生成的库文件: ${outputLib.absolutePath}")
        }
    }
}

tasks.register("build") {
    group = "build"
    dependsOn("buildRust")
}