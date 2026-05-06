package com.example.praktam_2407051014

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.praktam_2407051014.network.RetrofitClient
import com.example.praktam_2407051014.ui.theme.PrakTAM_2407051014Theme
import com.example.praktam_2407051014.Model.Rider

data class RiderEra(
    val nama: String,
    val imageUrl: String
)

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
    var listRider by remember { mutableStateOf<List<Rider>>(emptyList()) }
    var isLoadingData by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.instance.getRider()
            listRider = response
            isLoadingData = false
        } catch (e: Exception) {
            isLoadingData = false
            isError = true
        }
    }

    val listEraWithImages = listOf(
        RiderEra("Era Showa", "https://i.pinimg.com/736x/98/5d/32/985d328c659d1b265e7f353ee8097d8e.jpg"),
        RiderEra("Era Heisei", "https://i.pinimg.com/736x/41/ab/11/41ab115d78e2a28821a547e8bdca9009.jpg"),
        RiderEra("Era Reiwa", "https://i.pinimg.com/1200x/00/05/9e/00059eb39d36efac865ff666d6f5cfb4.jpg"),
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        if (isLoadingData) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (isError) {
            Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gagal Memuat Data", color = Color.Red, fontWeight = FontWeight.Bold)
                    Text("Cek koneksi internet Anda", textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Krider",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(listEraWithImages) { era ->
                            RiderEraCard(era = era)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Daftar Rider",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                items(listRider) { rider ->
                    RiderItem(rider = rider, snackbarHostState = snackbarHostState)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun RiderEraCard(era: RiderEra) {
    Card(
        modifier = Modifier.width(200.dp).border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = era.imageUrl,
                contentDescription = era.nama,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Text(
                text = era.nama,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun RiderItem(rider: Rider, snackbarHostState: SnackbarHostState) {
    var isFavorite by remember { mutableStateOf(false) }
    var isDetailLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth().border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                AsyncImage(
                    model = rider.imageUrl,
                    contentDescription = rider.nama,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = rider.nama, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = rider.deskripsi, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isDetailLoading = true
                            delay(2000)
                            snackbarHostState.showSnackbar("Berhasil memuat detail ${rider.nama}!")
                            isDetailLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isDetailLoading
                ) {
                    if (isDetailLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                    } else {
                        Text("Lihat Detail")
                    }
                }
            }
        }
    }
}