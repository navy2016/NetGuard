package eu.faircode.netguard

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.android.vending.billing.IInAppBillingService
import eu.faircode.netguard.data.Prefs
import org.json.JSONException
import org.json.JSONObject

class IAB(
    private val delegate: Delegate,
    context: Context,
) : ServiceConnection {
    private val context: Context = context.applicationContext
    private var service: IInAppBillingService? = null

    interface Delegate {
        fun onReady(iab: IAB)
    }

    fun bind() {
        Log.i(TAG, "Bind")
        val serviceIntent = Intent("com.android.vending.billing.InAppBillingService.BIND")
        serviceIntent.setPackage("com.android.vending")
        context.bindService(serviceIntent, this, Context.BIND_AUTO_CREATE)
    }

    fun unbind() {
        if (service != null) {
            Log.i(TAG, "Unbind")
            context.unbindService(this)
            service = null
        }
    }

    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
        Log.i(TAG, "Connected")
        service = IInAppBillingService.Stub.asInterface(binder)
        delegate.onReady(this)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        Log.i(TAG, "Disconnected")
        service = null
    }

    @Throws(RemoteException::class, JSONException::class)
    fun isAvailable(sku: String): Boolean {
        // 开放所有功能，不再检查商品可用性
        Log.i(TAG, "isAvailable $sku - always true (purchases removed)")
        return true
    }

    @Throws(RemoteException::class)
    fun updatePurchases() {
        // 已移除购买功能，跳过购买更新
        Log.i(TAG, "updatePurchases - skipped (purchases removed)")
    }

    @Suppress("DEPRECATION")
    @Throws(RemoteException::class)
    fun getBuyIntent(sku: String, isDonation: Boolean): PendingIntent? {
        // 已移除购买功能，返回null
        Log.i(TAG, "getBuyIntent $sku - returns null (purchases removed)")
        return null
    }

    companion object {
        private const val TAG = "NetGuard.IAB"
        private const val IAB_VERSION = 3

        fun setBought(sku: String, context: Context) {
            Log.i(TAG, "Bought $sku (ignored - purchases removed)")
        }

        /**
         * 开放所有专业功能 - 不再检查购买状态
         * 修改日期: 2026-02-27
         */
        fun isPurchased(sku: String, context: Context): Boolean {
            // 所有功能默认开放
            Log.i(TAG, "isPurchased $sku - returning true (purchases removed)")
            return true
        }

        /**
         * 开放所有专业功能 - 不再检查购买状态
         * 修改日期: 2026-02-27
         */
        fun isPurchasedAny(context: Context): Boolean {
            // 所有功能默认开放
            Log.i(TAG, "isPurchasedAny - returning true (purchases removed)")
            return true
        }

        private fun getResult(response: Int): String {
            return when (response) {
                0 -> "OK"
                1 -> "User canceled"
                2 -> "Service unavailable"
                3 -> "Billing unavailable"
                4 -> "Item unavailable"
                5 -> "Developer error"
                6 -> "Error"
                7 -> "Item already owned"
                8 -> "Item not owned"
                else -> "Unknown"
            }
        }
    }
}
