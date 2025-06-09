package com.iruka.tachibana.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.iruka.tachibana.R


enum class CommentType {
    Mind, Brain, Heart, Life, ExtraMind,Day7, Day14, Day21, Day28,Day14Rare
}

data class TachibanaLine(
    val text: String,
    val type: CommentType
)

fun getDay7Comments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.Day7_comments_0), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_1), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_2), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_3), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_4), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_5), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_6), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_7), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_8), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_9), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_10), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_11), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_12), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_13), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_14), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_15), CommentType.Day7),
    TachibanaLine(context.getString(R.string.Day7_comments_16), CommentType.Day7),
)

fun getDay14Comments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.Day14_comments_0), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_1), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_2), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_3), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_4), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_5), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_6), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_7), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_8), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_9), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_10), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_11), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_12), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_13), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_14), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_15), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_16), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14_comments_17), CommentType.Day14),
)


fun getDay15Comments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.Day14Rare_comments_0), CommentType.Day14),
    TachibanaLine(context.getString(R.string.Day14Rare_comments_1), CommentType.Day14Rare)
    // 追加があればここに続けてください
)
fun getDay21Comments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.Day21_comments_0), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_1), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_2), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_3), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_4), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_5), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_6), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_7), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_8), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_9), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_10), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_11), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_12), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_13), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_14), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_15), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_16), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_17), CommentType.Day21),
    TachibanaLine(context.getString(R.string.Day21_comments_18), CommentType.Day21)
)

fun getDay28Comments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.Day28_comments_0), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_1), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_2), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_3), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_4), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_5), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_6), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_7), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_8), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_9), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_10), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_11), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_12), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_13), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_14), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_15), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_16), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_17), CommentType.Day28),
    TachibanaLine(context.getString(R.string.Day28_comments_18), CommentType.Day28)
)


fun getMindComments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.mind_comment_0), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_1), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_2), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_3), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_4), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_5), CommentType.Mind),
    TachibanaLine(context.getString(R.string.mind_comment_6), CommentType.Mind)
)

fun getBrainComments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.brain_comment_0), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_1), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_2), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_3), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_4), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_5), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_6), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_7), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_8), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_9), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_10), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_11), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_12), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_13), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_14), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_15), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_16), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_17), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_18), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_19), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_20), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_21), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_22), CommentType.Brain),
    TachibanaLine(context.getString(R.string.brain_comment_23), CommentType.Brain)
)


fun getHeartComments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.heart_comment_0), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_1), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_2), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_3), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_4), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_5), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_6), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_7), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_8), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_9), CommentType.Heart),
    TachibanaLine(context.getString(R.string.heart_comment_10), CommentType.Heart)
)


fun getLifeComments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.life_comment_0), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_1), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_2), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_3), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_4), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_5), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_6), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_7), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_8), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_9), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_10), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_11), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_12), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_13), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_14), CommentType.Life),
    TachibanaLine(context.getString(R.string.life_comment_15), CommentType.Life)
)



fun getExtraMindComments(context: Context): List<TachibanaLine> = listOf(
    TachibanaLine(context.getString(R.string.neuro_comment_0), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_1), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_2), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_3), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_4), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_5), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_6), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_7), CommentType.Life),
    TachibanaLine(context.getString(R.string.neuro_comment_8), CommentType.Life)
)