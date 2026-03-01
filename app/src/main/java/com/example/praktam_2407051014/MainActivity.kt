package com.example.praktam_2407051014

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.praktam_2407051014.ui.theme.PrakTAM_2407051014Theme
import Model.RiderSource

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
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(listRider) { rider ->
            Column(modifier = Modifier.padding(vertical = 12.dp)) {

                Image(
                    painter = painterResource(id = rider.imagesRes),
                    contentDescription = rider.nama,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nama: ${rider.nama}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Deskripsi: ${rider.deskripsi}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}