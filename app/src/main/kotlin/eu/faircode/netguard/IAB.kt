package eu.faircode.netguard

import android.content.Context
import android.util.Log

/**
 * IAB - 应用内购买模块（已移除）
 * 所有 Pro 功能默认开放，不再依赖 Google Play 计费服务。
 * 修改日期: 2026-02-27
 *
 * 保留原有构造函数签名和公开方法签名，确保调用方无需修改。
 * 内部实现已清空，不再连接 Google Play 服务。
 */
class IAB(
    private val delegate: Delegate,
    context: Context,
) {
    // 保留字段签名，但不再使用
    @Suppress("unused")
    private val context: Context = context.applicationContext

    interface Delegate {
        fun onReady(iab: IAB)
    }

    /**
     * 原逻辑：绑定 Google Play 计费服务
     * 现逻辑：跳过绑定，直接回调 onReady
     */
    fun bind() {
        Log.i(TAG, "Bind - skipped (purchases removed)")
        delegate.onReady(this)
    }

    /**
     * 原逻辑：解绑 Google Play 计费服务
     * 现逻辑：无操作
     */
    fun unbind() {
        Log.i(TAG, "Unbind - skipped (purchases removed)")
    }

    fun isAvailable(sku: String): Boolean {
        Log.i(TAG, "isAvailable $sku - always true (purchases removed)")
        return true
    }

    fun updatePurchases() {
        Log.i(TAG, "updatePurchases - skipped (purchases removed)")
    }

    fun getBuyIntent(sku: String, isDonation: Boolean): android.app.PendingIntent? {
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
