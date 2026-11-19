package com.example.planify

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    progressOverride: Float? = null,
    onLoadingComplete: () -> Unit = {}
) {
    // State animasi loading (jika tidak di-override oleh preview)
    var targetProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        if (progressOverride == null) {
            // Simulasi loading menuju 100%
            delay(300L)
            targetProgress = 0.4f
            delay(400L)
            targetProgress = 0.7f
            delay(500L)
            targetProgress = 1.0f
            delay(200L)
            onLoadingComplete()
        }
    }

    val currentProgress = progressOverride ?: targetProgress
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(durationMillis = 600),
        label = "progressAnimation"
    )

    // Warna tema SplashScreen
    val backgroundColor = Color(0xFFF7FAFF)
    val navyColor = Color(0xFF0F172A)
    val primaryBlue = Color(0xFF2563EB)
    val lightBlue = Color(0xFF93C5FD)
    val trackBlue = Color(0xFFE2E8F0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // 1. Dekorasi Gelombang di Bagian Atas menggunakan Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .align(Alignment.TopCenter)
        ) {
            val width = size.width
            val height = size.height

            // Gelombang Atas 1 (Transparan terang)
            val path1 = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height * 0.5f)
                cubicTo(width * 0.3f, height * 0.9f, width * 0.7f, height * 0.1f, width, height * 0.6f)
                lineTo(width, 0f)
                close()
            }
            drawPath(
                path = path1,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFD6E4FF).copy(alpha = 0.7f), Color(0xFFEBF2FF).copy(alpha = 0.3f))
                )
            )

            // Gelombang Atas 2 (Lebih dinamis)
            val path2 = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height * 0.3f)
                cubicTo(width * 0.25f, height * 0.7f, width * 0.75f, height * 0.2f, width, height * 0.4f)
                lineTo(width, 0f)
                close()
            }
            drawPath(
                path = path2,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF93C5FD).copy(alpha = 0.4f), Color(0xFFBFDBFE).copy(alpha = 0.1f))
                )
            )
        }

        // 2. Dekorasi Gelombang di Bagian Bawah menggunakan Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .align(Alignment.BottomCenter)
        ) {
            val width = size.width
            val height = size.height

            // Gelombang Bawah 1
            val pathBottom1 = Path().apply {
                moveTo(0f, height * 0.4f)
                cubicTo(width * 0.3f, height * 0.1f, width * 0.7f, height * 0.8f, width, height * 0.3f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                path = pathBottom1,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFEBF2FF).copy(alpha = 0.5f), Color(0xFF93C5FD).copy(alpha = 0.3f))
                )
            )

            // Gelombang Bawah 2
            val pathBottom2 = Path().apply {
                moveTo(0f, height * 0.6f)
                cubicTo(width * 0.4f, height * 0.2f, width * 0.65f, height * 0.9f, width, height * 0.5f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                path = pathBottom2,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF60A5FA).copy(alpha = 0.3f), Color(0xFF3B82F6).copy(alpha = 0.5f))
                )
            )
        }

        // 3. Konten Utama di Tengah & Bawah
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo Kalender dengan Centang & Ring Melingkar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Ilustrasi Logo Calendar menggunakan Canvas
                    Canvas(modifier = Modifier.size(130.dp)) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        // Lingkaran orbit / ring melingkar di sekeliling kalender
                        drawArc(
                            brush = Brush.sweepGradient(
                                colors = listOf(Color(0xFF3B82F6), Color(0xFF93C5FD), Color(0xFF60A5FA))
                            ),
                            startAngle = -30f,
                            sweepAngle = 260f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Badan Kalender
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF2563EB), Color(0xFF1D4ED8))
                            ),
                            size = androidx.compose.ui.geometry.Size(canvasWidth * 0.85f, canvasHeight * 0.75f),
                            topLeft = Offset(canvasWidth * 0.075f, canvasHeight * 0.2f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(24f, 24f)
                        )

                        // Lembar Kalender Putih di dalam
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFFE2E8F0))
                            ),
                            size = androidx.compose.ui.geometry.Size(canvasWidth * 0.7f, canvasHeight * 0.55f),
                            topLeft = Offset(canvasWidth * 0.15f, canvasHeight * 0.35f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                        )

                        // Ikon Centang (Checkmark) di tengah kalender
                        val checkPath = Path().apply {
                            moveTo(canvasWidth * 0.35f, canvasHeight * 0.62f)
                            lineTo(canvasWidth * 0.47f, canvasHeight * 0.74f)
                            lineTo(canvasWidth * 0.68f, canvasHeight * 0.48f)
                        }
                        drawPath(
                            path = checkPath,
                            color = Color(0xFF2563EB),
                            style = Stroke(width = 10f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
                        )
                    }

                    // Penjepit Atas Kalender (Kiri & Kanan)
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .width(80.dp)
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 12.dp, height = 26.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF3B82F6))
                        )
                        Box(
                            modifier = Modifier
                                .size(width = 12.dp, height = 26.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF3B82F6))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Judul "Planify"
                Text(
                    text = "Planify",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = navyColor,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subjudul "Study Planner" dengan Garis Dekoratif Kiri & Kanan
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(1.dp)
                            .background(lightBlue)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Study Planner",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(1.dp)
                            .background(lightBlue)
                    )
                }
            }

            // Bagian Bawah: Teks Status, Progress Bar, dan Persentase
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Menyiapkan Planify...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = navyColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Progress Bar Biru
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = primaryBlue,
                    trackColor = trackBlue,
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Persentase Loading
                val percentage = (animatedProgress * 100).toInt()
                Text(
                    text = "$percentage%",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Preview dengan progress 70% sesuai permintaan referensi
@Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun SplashScreenPreview() {
    SplashScreen(progressOverride = 0.70f)
}
