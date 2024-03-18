package com.hightech.cryptoapp.avenger.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hightech.cryptoapp.R
import androidx.compose.foundation.lazy.items
import com.hightech.cryptoapp.avenger.ui.AvengerItemUi

@Composable
fun AvengerList(
    modifier: Modifier = Modifier,
    contentModifier: Modifier,
    items: List<AvengerItemUi>,
    onNavigateToCryptoDetails: (AvengerItemUi) -> Unit
) {
    Column(
        modifier = contentModifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(items) { avenger ->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToCryptoDetails(avenger) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(20.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = modifier.weight(2f)
                        ) {
                            Box(
                                modifier = modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFFFCFCFF))
                                    .size(50.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = avenger.image),
                                    contentDescription = "Icon",
                                    modifier = modifier
                                        .size(50.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = modifier.weight(5f)
                        ) {
                            Text(
                                text = avenger.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Start
                            )

                            Text(
                                text = avenger.rating,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
                Divider(color = Color.Gray)
            }
        }
    }
}