package com.iruka.tachibana.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource


@Composable
fun HomeModal(onClose: () -> Unit) {
    val context = LocalContext.current
    ModalWrapper(onClose = onClose) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 背景ブラー＋暗く
            Box(
                modifier = Modifier
                    .fillMaxSize()


            )

            // モーダルの背景BOX
            Box(
                modifier = Modifier
                    .offset(y = 310.dp)
                    .size(width = 333.dp, height = 316.dp)
                    .align(Alignment.TopCenter)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .background(Color(0xFFA9896D), shape = RoundedCornerShape(8.dp))
                    .zIndex(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.main_home_download_thanks),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = YuseiMagic
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFFF4E5CC),
                                        fontSize = 16.sp,
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_1)) // 断ち花
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_2)) // は、あなたの
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_3)) // "やめたい気持ち"と\n
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_4)) // いっしょに歩む〜
                                    append(stringResource(R.string.main_home_message_5)) // 応援してくれる〜
                                    append(stringResource(R.string.main_home_message_6)) // もしよければ、
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xFFA7D7C5),
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_7)) // ちょっと
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontFamily = YuseiMagic
                                    )
                                ) {
                                    append(stringResource(R.string.main_home_message_8)) // だけ力を貸してくれませんか？
                                }
                            },
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                        Spacer(modifier = Modifier.height(50.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(27.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(width = 137.dp, height = 36.dp)
                                    .background(Color(0xFFF4E5CC), shape = RoundedCornerShape(4.dp))
                                    .border(
                                        width = Dp.Hairline,
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color(0xFF7C5050),
                                                Color(0xFFCF8686),
                                                Color(0xFFF4E5CC)
                                            ),
                                            startY = 0f,
                                            endY = Float.POSITIVE_INFINITY
                                        ),
                                        shape = RoundedCornerShape(4.dp)
                                    ).clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://lucaverse-site.vercel.app/")
                                        )
                                        context.startActivity(intent)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.main_home_support),
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = YuseiMagic
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(width = 137.dp, height = 36.dp)
                                    .background(Color(0xFFDDD1C0), shape = RoundedCornerShape(4.dp))
                                    .clickable { onClose() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.main_home_not_now),
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    fontFamily = YuseiMagic
                                )
                            }
                        }
                    }
                }

                // 上に飛び出す立ち絵
                Image(
                    painter = painterResource(id = R.drawable.home_thanks),
                    contentDescription = "thanks image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(229.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 100.dp)
                        .zIndex(2f)
                )
            }
        }
    }
