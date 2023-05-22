package com.example.wheredatingapp.ui

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class Icons(context: Context) {

    /*
    val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context, R.color.black)
        BitmapHelper.vectorToBitmap(context, R.drawable.ciudad, color)
    }

     */

}

fun getMarkerIcon(color: String?): BitmapDescriptor? {
    val hsv = FloatArray(3)
    Color.colorToHSV(Color.parseColor(color), hsv)
    return BitmapDescriptorFactory.defaultMarker(hsv[0])
}