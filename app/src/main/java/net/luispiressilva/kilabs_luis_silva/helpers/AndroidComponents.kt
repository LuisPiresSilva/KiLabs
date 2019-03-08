package net.luispiressilva.kilabs_luis_silva.helpers

import android.content.Context

/**
 * Created by Luis Silva on 28/01/2019.
 */


//DEVELOPER NOTES:
//Instead of returning always 24dp as default several known values should be checked before, based on android version
/**
 * helper function that returns the status bar height (returns default 24dp in case of missing)
 */
fun hpGetStatusBarHeight(ctx: Context): Int {
    var result = Utils.converters.hpConvertDpToPixel(24, ctx) //default not to bad value
    val resourceId = ctx.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = ctx.resources.getDimensionPixelSize(resourceId)
    }
    return result
}