package com.example.todoapp.util

import android.content.Context
import android.content.ContextParams
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {
//    doWork class have a Asynchornous function doWork() that trigger the creation of notification
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
//            inputData is a key-value object that contain information to be processed within the doWork()
            inputData.getString("title").toString(),
            inputData.getString("message").toString()
        )
        return Result.success()
    }
}