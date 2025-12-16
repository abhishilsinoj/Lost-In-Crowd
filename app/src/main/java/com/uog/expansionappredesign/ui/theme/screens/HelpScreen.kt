package com.uog.expansionappredesign.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uog.expansionappredesign.R
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
        /**
         * Displays a user tutorial screen.
         * This static screen lists instructions on how to use the application.
         * @param onBackClick Lambda callback to navigate back to the previous screen.
         */
fun HelpScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.how_to_use)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HelpItem(
                title = stringResource(R.string.h1T),
                desc = stringResource(R.string.h1d)
            )

            HelpItem(
                title = stringResource(R.string.h2T),
                desc = stringResource(R.string.h2d)
            )

            HelpItem(
                title = stringResource(R.string.h3T),
                desc = stringResource(R.string.h3d)
            )

            HelpItem(
                title = stringResource(R.string.h4T),
                desc = stringResource(R.string.h4d)
            )

            HelpItem(
                title = stringResource(R.string.h5T),
                desc = stringResource(R.string.h5d)
            )
        }
    }
}

/**
 * Renders a card containing a  title and a description.
 * @param title The header text.
 * @param desc The detailed explanation text.
 */
@Composable
fun HelpItem(title: String, desc: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}