package io.github.lkleno.mobilesensing.audio

import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import io.github.lkleno.mobilesensing.MainActivity
import io.github.lkleno.mobilesensing.R
import java.util.*

class Audio(private var context: MainActivity) : TextToSpeech.OnInitListener
{
    private var tts: TextToSpeech? = TextToSpeech(context,this)

    override fun onInit(status: Int){
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported!")
            }
        }
        else{
            Log.e("TTS","Initialization Failed!")
        }
    }

    fun onDestroy()
    {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null)
    }
    fun Audio(numOfItems: Int, item: String, location:String){
        if (numOfItems > 1) {
            val numString = getNumber(numOfItems)
            val locationSentence = getLocation(location)
            val itemCorrected = getItem(item)
            speakOut("$numString $itemCorrected $locationSentence")
        }
        else{
            val locationSentence = getLocationOne(location)
            val itemCorrected = getItemOne(item)
            speakOut("$itemCorrected $locationSentence")
        }
    }

    private fun getLocation(location: String): String{
        return when (location) {
            context.getString(R.string.string_lowerleft) -> "have been detected at the lower left side of the screen."
            context.getString(R.string.string_lowerright) -> "have been detected at the lower right side of the screen."
            context.getString(R.string.string_center) -> "have been detected at the center of the screen."
            context.getString(R.string.string_upperright) -> "have been detected at the upper right side of the screen."
            context.getString(R.string.string_upperleft) -> "have been detected at the upper left side of the screen."
            else -> "WRONG LOCATION"
        }
    }
    private fun getNumber(numOfItems: Int) : String{
        return when (numOfItems) {
            2 -> "Two"
            3 -> "Three"
            4 -> "Four"
            5 -> "Five"
            6 -> "Six"
            7 -> "Seven"
            8 -> "Eight"
            9 -> "Nine"
            10 -> "Ten"
            else -> "WRONG NUMBER"
        }
    }
    private fun getLocationOne(location: String):String{
        return when (location) {
            context.getString(R.string.string_lowerleft) -> "has been detected at the lower left side of the screen."
            context.getString(R.string.string_lowerright) -> "has been detected at the lower right side of the screen."
            context.getString(R.string.string_center) -> "has been detected at the center of the screen."
            context.getString(R.string.string_upperright) -> "has been detected at the upper right side of the screen."
            context.getString(R.string.string_upperleft) -> "has been detected at the upper left side of the screen."
            else -> location
        }
    }
    private fun getItem(item:String):String{
        return when (item) {
            context.getString(R.string.string_clothing) -> "Clothes"
            context.getString(R.string.string_coin) -> "Coins"
            context.getString(R.string.string_footwear) -> "Footwears"
            context.getString(R.string.string_necklace) -> "Necklaces"
            context.getString(R.string.string_watch) -> "Watches"
            context.getString(R.string.string_wheelchair) -> "Wheelchairs"
            else -> item
        }
    }
    private fun getItemOne(item:String):String{
        return when (item) {
            context.getString(R.string.string_coin) -> "A coin"
            context.getString(R.string.string_necklace) -> "A Necklace"
            context.getString(R.string.string_watch) -> "A watch"
            context.getString(R.string.string_wheelchair) -> "A Wheelchair"
            else -> item
        }
    }

    fun AudioTest() {Audio(3,context.getString(R.string.string_watch), context.getString(R.string.string_upperleft))}
}