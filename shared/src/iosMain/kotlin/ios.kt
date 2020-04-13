package com.example

import platform.UIKit.UIDevice
import cocoapods.AFNetworking.AFHTTPSessionManager

actual fun platformName(): String {

    // just testing if dependencies fetched through cocoapods work
    val manager = AFHTTPSessionManager()
    
    return UIDevice.currentDevice.systemName()
}
