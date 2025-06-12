package com.iruka.tachibana.data

import android.content.Context
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.MamaUiState

data class MamaCommentLine(
    val text: String,
    val type: MamaUiState
)

// --- 状態別コメント関数（strings.xml依存） ---
fun getEatingComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_eating_0), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_1), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_2), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_3), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_4), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_5), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_6), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_7), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_8), MamaUiState.Eating),
    MamaCommentLine(context.getString(R.string.mama_eating_9), MamaUiState.Eating)
)

fun getCuteComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_cute_0), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_1), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_2), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_3), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_4), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_5), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_6), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_7), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_8), MamaUiState.Cute),
    MamaCommentLine(context.getString(R.string.mama_cute_9), MamaUiState.Cute)
)

fun getByeByeComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_byebye_0), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_1), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_2), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_3), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_4), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_5), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_6), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_7), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_8), MamaUiState.ByeBye),
    MamaCommentLine(context.getString(R.string.mama_byebye_9), MamaUiState.ByeBye)
)

fun getBanzaiComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_banzai_0), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_1), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_2), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_3), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_4), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_5), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_6), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_7), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_8), MamaUiState.Banzai),
    MamaCommentLine(context.getString(R.string.mama_banzai_9), MamaUiState.Banzai)
)

fun getFriedMaskComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_fried_mask_0), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_1), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_2), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_3), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_4), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_5), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_6), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_7), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_8), MamaUiState.FriedMask),
    MamaCommentLine(context.getString(R.string.mama_fried_mask_9), MamaUiState.FriedMask)
)

fun getRelaxComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_relax_0), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_1), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_2), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_3), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_4), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_5), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_6), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_7), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_8), MamaUiState.Relax),
    MamaCommentLine(context.getString(R.string.mama_relax_9), MamaUiState.Relax)
)

fun getShyComments(context: Context): List<MamaCommentLine> = listOf(
    MamaCommentLine(context.getString(R.string.mama_shy_0), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_1), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_2), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_3), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_4), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_5), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_6), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_7), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_8), MamaUiState.Shy),
    MamaCommentLine(context.getString(R.string.mama_shy_9), MamaUiState.Shy)
)
// --- 状態に応じたマップ型取得 ---
fun getMamaCommentsForState(context: Context, state: MamaUiState): List<MamaCommentLine> {
    return when (state) {
        MamaUiState.Eating -> getEatingComments(context)
        MamaUiState.Cute -> getCuteComments(context)
        MamaUiState.ByeBye -> getByeByeComments(context)
        MamaUiState.Banzai -> getBanzaiComments(context)
        MamaUiState.FriedMask -> getFriedMaskComments(context)
        MamaUiState.Relax -> getRelaxComments(context)
        MamaUiState.Shy -> getShyComments(context)
    }
}