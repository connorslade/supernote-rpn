package com.connorslade.supernote_rpn

import android.os.IBinder
import android.os.Parcel
import android.util.Log

class Supernote {
    companion object {
        // Reimplementation of sendWritable from PluginHost.apk (com.ratta.supernote.eventlibrary.HandWriteClient)
        fun setWritable(writable: Boolean) {
            var binder = Class.forName("android.os.ServiceManager")
                .getMethod("getService", String::class.java)
                .invoke(null, "service_myservice") as IBinder


            var message = Parcel.obtain()
            message.writeInterfaceToken("android.demo.IMyService")
            message.writeString("superNoteNote")
            message.writeInt(1) // 1 entry

            // start at (0, 0)
            message.writeInt(0)
            message.writeInt(0)

            // no idea what this means. just some sentanal values ig
            if (writable) {
                message.writeInt(18888)
                message.writeInt(18888)
            } else {
                message.writeInt(19999)
                message.writeInt(19999)
            }
            message.writeInt(0)

            // send it off!
            var response = Parcel.obtain()
            binder.transact(1, message, response, 0)
            response.recycle()
            message.recycle()
        }
    }
}
