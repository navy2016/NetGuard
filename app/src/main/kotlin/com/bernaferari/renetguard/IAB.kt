package com.bernaferari.renetguard

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log

/**
 * IAB - 应用内购买模块（已移除购买逻辑）
 * 所有 Pro 功能默认开放，不再依赖 Google Play 计费服务。
 * 保留原有签名，调用方无需修改。
 * 修改日期: 2026-02-27
 */
class IAB(
    private val delegate: Delegate,
    context: Context,
) : ServiceConnection {
    @Suppress("unused")
    private val context: Context = context.applicationContext
    private var service: IBinder? = null

    interface Delegate {
        fun onReady(iab: IAB)
    }

    fun bind() {
        Log.i(TAG, "Bind - skipped (purchases removed)")
        delegate.onReady(this)
    }

    fun unbind() {
        Log.i(TAG, "Unbind - skipped (purchases removed)")
        service = null
    }

    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
        Log.i(TAG, "Connected (unused)")
        service = binder
        delegate.onReady(this)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        Log.i(TAG, "Disconnected (unused)")
        service = null
    }

    fun isAvailable(sku: String): Boolean {
        Log.i(TAG, "isAvailable $sku - always true (purchases removed)")
        return true
    }

    fun updatePurchases() {
        Log.i(TAG, "updatePurchases - skipped (purchases removed)")
    }

    fun getBuyIntent(sku: String, isDonation: Boolean): PendingIntent? {
        Log.i(TAG, "getBuyIntent $sku - returns null (purchases removed)")
        return null
    }

    companion object {
        private const val TAG = "NetGuard.IAB"

        fun setBought(sku: String, context: Context) {
            Log.i(TAG, "Bought $sku (ignored - purchases removed)")
        }

        fun isPurchased(sku: String, context: Context): Boolean {
            Log.i(TAG, "isPurchased $sku - returning true (purchases removed)")
            return true
        }

        fun isPurchasedAny(context: Context): Boolean {
            Log.i(TAG, "isPurchasedAny - returning true (purchases removed)")
            return true
        }
    }
}
