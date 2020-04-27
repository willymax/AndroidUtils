package ke.co.basecode.images

import android.content.Context
import android.os.Handler
import android.os.Looper
import ke.co.basecode.utils.BaseUtils.Companion.getCompressed
import java.io.File
import java.io.IOException
import java.util.*

class ImageCompressTask : Runnable {
    private var mContext: Context
    private var originalPaths: MutableList<String> = ArrayList()
    private val mHandler = Handler(Looper.getMainLooper())
    private val result: MutableList<File?> = ArrayList()
    private var mIImageCompressTaskListener: IImageCompressTaskListener?
    private var width: Int
    private var height: Int

    constructor(
        context: Context,
        width: Int,
        height: Int,
        path: String,
        compressTaskListener: IImageCompressTaskListener?
    ) {
        originalPaths.add(path)
        mContext = context
        mIImageCompressTaskListener = compressTaskListener
        this.width = width
        this.height = height
    }

    constructor(
        context: Context,
        width: Int,
        height: Int,
        paths: MutableList<String>,
        compressTaskListener: IImageCompressTaskListener?
    ) {
        originalPaths = paths
        mContext = context
        mIImageCompressTaskListener = compressTaskListener
        this.width = width
        this.height = height
    }

    override fun run() {
        try {
            //Loop through all the given paths and collect the compressed file from Util.getCompressed(Context, String)
            for (path in originalPaths) {
                val file = getCompressed(mContext, path, width, height)
                //add it!
                result.add(file)
            }
            //use Handler to post the result back to the main Thread
            mHandler.post {
                if (mIImageCompressTaskListener != null) mIImageCompressTaskListener!!.onComplete(
                    result
                )
            }
        } catch (ex: IOException) {
            //There was an error, report the error back through the callback
            mHandler.post {
                if (mIImageCompressTaskListener != null) mIImageCompressTaskListener!!.onError(
                    ex
                )
            }
        }
    }
}