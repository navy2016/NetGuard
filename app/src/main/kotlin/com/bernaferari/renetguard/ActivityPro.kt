package com.bernaferari.renetguard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bernaferari.renetguard.ui.theme.NetGuardThemeFromPrefs

class ActivityPro : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Create - All pro features enabled")
        super.onCreate(savedInstanceState)

        setContent {
            NetGuardThemeFromPrefs {
                ProContent()
            }
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroy")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "NetGuard.Pro"

        // ========== SKU 常量（其他模块引用这些值，必须保留） ==========
        const val SKU_LOG = "log"
        const val SKU_FILTER = "filter"
        const val SKU_NOTIFY = "notify"
        const val SKU_SPEED = "speed"
        const val SKU_THEME = "theme"
        const val SKU_PRO1 = "pro1"
        const val SKU_SUPPORT1 = "support1"
        const val SKU_SUPPORT2 = "support2"
        const val SKU_DONATION = "donation"
        // ==============================================================
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProContent() {
    val items = listOf(
        ProItem(ActivityPro.SKU_LOG, R.string.title_pro_log),
        ProItem(ActivityPro.SKU_FILTER, R.string.title_pro_filter),
        ProItem(ActivityPro.SKU_NOTIFY, R.string.title_pro_notify),
        ProItem(ActivityPro.SKU_SPEED, R.string.title_pro_speed),
        ProItem(ActivityPro.SKU_THEME, R.string.title_pro_theme),
        ProItem(ActivityPro.SKU_PRO1, R.string.title_pro_all),
        ProItem(ActivityPro.SKU_SUPPORT1, R.string.title_pro_dev),
        ProItem(ActivityPro.SKU_SUPPORT2, R.string.title_pro_dev),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_pro)) },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.title_pro_description),
                style = MaterialTheme.typography.bodyMedium,
            )

            items.forEach { item ->
                ProRow(
                    title = stringResource(item.titleRes),
                )
            }
        }
    }
}

@Composable
private fun ProRow(
    title: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = stringResource(R.string.title_pro_bought),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

private data class ProItem(
    val sku: String,
    val titleRes: Int,
)
