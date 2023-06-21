package com.eagskunst.morky

import android.app.Application
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.MemoryChunkType
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MorkyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initFresco()
    }

    private fun initFresco() {
        val diskCacheConfig = DiskCacheConfig.newBuilder(this)
            .setMaxCacheSize(100L * ByteConstants.MB)
            .build()
        Fresco.initialize(
            this,
            ImagePipelineConfig.newBuilder(this)
                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                .setDiskCacheEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build(),
        )
    }
}
