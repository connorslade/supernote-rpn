package com.connorslade.supernote_rpn

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class Module(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext), LifecycleEventListener {

    private var window: View? = null
    private var windowManager: WindowManager? = null

    init {
        reactContext.addLifecycleEventListener(this)
    }

    override fun getName() = "Module"

    @ReactMethod
    fun toggle(promise: Promise) = reactContext.runOnUiQueueThread {
        if (window != null) hide() else show()
        promise.resolve(window != null)
    }

    private fun hide() {
        window?.let { runCatching { windowManager?.removeView(it) } }
        window = null
    }

    private fun show() {
        val ctx = reactContext.applicationContext
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager = wm

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT,
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 200
            y = 400
        }

        val root = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                setStroke(2, Color.BLACK)
                cornerRadius = 12f
            }
            setPadding(16, 8, 16, 16)
        }

        val title = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            addView(TextView(ctx).apply {
                text = "Calculator"
                textSize = 20f
                setTextColor(Color.BLACK)
            }, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
            addView(TextView(ctx).apply {
                text = "✕"
                textSize = 20f
                setTextColor(Color.BLACK)
                setPadding(24, 8, 8, 8)
                setOnClickListener { hide() }
            })
        }

        root.addView(title, LinearLayout.LayoutParams(480, LinearLayout.LayoutParams.WRAP_CONTENT))
        root.addView(TextView(ctx).apply {
            text = "guess it works"
            textSize = 18f
            setTextColor(Color.BLACK)
            setPadding(0, 24, 0, 24)
        })

        wm.addView(root, params)
        window = root
    }

    override fun onHostResume() {}
    override fun onHostPause() {}
    override fun onHostDestroy() {
        reactContext.runOnUiQueueThread { hide() }
        reactContext.removeLifecycleEventListener(this)
    }
}
