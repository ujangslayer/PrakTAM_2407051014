package com.example.praktam_2407051014

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam_2407051014.ui.theme.PrakTAM_2407051014Theme
import Model.RiderSource
import Model.Rider

val PrimaryBlue = Color(0xFF0288D1)
val LightBlueCard = Color(0xFFE1F5FE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM_2407051014Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RiderScr(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RiderScr(modifier: Modifier = Modifier) {
    val listRider = RiderSource.rider

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Krider",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryBlue,
            modifier = Modifier.padding(bottom = 20.dp, top = 8.dp)
        )

        listRider.forEach { rider ->
            RiderItem(rider = rider)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RiderItem(rider: Rider) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = PrimaryBlue,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlueCard)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = rider.imagesRes),
                contentDescription = rider.nama,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = rider.nama,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = rider.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text(
                        text = "Detail Rider",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}