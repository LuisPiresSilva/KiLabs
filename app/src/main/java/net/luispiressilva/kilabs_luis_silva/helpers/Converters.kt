package net.luispiressilva.kilabs_luis_silva.helpers

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics


/**
 * Created by Luis Silva on 28/01/2019.
 */
class ConvertersUtils(internal val app: Application) {


    fun hpConvertDpToPixel(dp: Int, context: Context): Int {
        return convertDpToPixel(dp, context)
    }

    fun hpConvertPixelsToDp(px: Int, context: Context): Int {
        return convertPixelsToDp(px, context)
    }

}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Int, context: Context): Int {
    return dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Int, context: Context): Int {
    return px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}