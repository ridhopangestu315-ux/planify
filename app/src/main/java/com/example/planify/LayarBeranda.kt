package com.example.planify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Komponen KepalaBeranda menampilkan sapaan kepada pengguna di bagian atas dashboard.
 */
@Composable
fun KepalaBeranda() {
    val warnaNavy = Color(0xFF0F172A)
    val warnaPutih = Color(0xFFFFFFFF)
    val warnaBiruTerang = Color(0xFF93C5FD)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(warnaNavy)
            .padding(24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Selamat Datang,",
                color = warnaBiruTerang,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Ridho",
                color = warnaPutih,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Siap menyelesaikan kegiatan hari ini?",
            color = warnaPutih,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PratinjauKepalaBeranda() {
    KepalaBeranda()
}
