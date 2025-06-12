package com.iruka.tachibana

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.google.android.gms.ads.MobileAds


class TachibanaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // MobileAds初期化
        MobileAds.initialize(this)
    }
}

//class TachibanaApplication : Application(), ImageLoaderFactory {
  //  override fun newImageLoader(context: Context): ImageLoader {
    //    return ImageLoader.Builder(context)
      //      .components {
        //        if (Build.VERSION.SDK_INT >= 28) {
          //          add(ImageDecoderDecoder.Factory())
            //    } else {
              //      add(GifDecoder.Factory())
                //}
           // }
           // .build()
   // }/
//}
