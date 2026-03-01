package eu.faircode.netguard

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.faircode.netguard.ui.theme.NetGuardThemeFromPrefs

class ActivityPro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Create - All pro features enabled")
        super.onCreate(savedInstanceState)

        setContent {
            NetGuardThemeFromPrefs {
                ProScreen()
            }
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroy")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "NetGuard.Pro"
    }
}

@Composable
fun ProScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val proFeatures = listOf(
        ProFeature(R.string.title_pro_log, R.string.msg_pro_log),
        ProFeature(R.string.title_pro_filter, R.string.msg_pro_filter),
        ProFeature(R.string.title_pro_notify, R.string.msg_pro_notify),
        ProFeature(R.string.title_pro_speed, R.string.msg_pro_speed),
        ProFeature(R.string.title_pro_theme, R.string.msg_pro_theme),
        ProFeature(R.string.title_pro_all, R.string.msg_pro_all),
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
                text = stringResource(R.string.title_pro_description_enabled),
                style = MaterialTheme.typography.bodyMedium,
            )

            proFeatures.forEach { feature ->
                ProFeatureCard(
                    title = stringResource(feature.titleRes),
                    description = stringResource(feature.descriptionRes),
                )
            }

            // 支持开发者部分
            Text(
                text = stringResource(R.string.title_pro_support),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                text = stringResource(R.string.msg_pro_support),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ProFeatureCard(
    title: String,
    description: String,
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    androidx.compose.material.icons.Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private data class ProFeature(
    val titleRes: Int,
    val descriptionRes: Int,
)
