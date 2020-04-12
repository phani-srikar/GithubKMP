package com.example

import gzip.*
import platform.Foundation.NSData
import platform.Foundation.NSFileManager

open class ZipUtils {
    fun printContents(pathToZipFile: String) {
        val compressedData : NSData = NSFileManager.defaultManager.contentsAtPath(pathToZipFile)!!
        print("compressed:" + compressedData.toString())
        val uncompressedData = compressedData.gunzippedData()
        print(uncompressedData.toString())
    }
}
