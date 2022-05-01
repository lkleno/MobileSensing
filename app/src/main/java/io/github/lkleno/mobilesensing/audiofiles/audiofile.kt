package io.github.lkleno.mobilesensing

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import androidx.camera.core.*
import io.github.lkleno.mobilesensing.MainActivity

class audio(private var context: MainActivity){
    fun StartPlayAudio(
        numOfItems: Int,
        numOfItemsBool: Boolean,
        item: String,
        itemBool: Boolean,
        location: String,
        locationBool: Boolean
    ) {
        if (!numOfItemsBool) {
            if (numOfItems > 1) {
                when (numOfItems) {
                    2 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.two),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    3 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.three),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    4 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.four),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    5 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.five),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    6 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.six),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    7 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.seven),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    8 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.eight),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    9 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.nine),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    10 -> PlayAudio(
                        MediaPlayer.create(context, R.raw.ten),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                }
            } else {
                StartPlayAudio(numOfItems, true, item, itemBool, location, locationBool)
            }
        } else if (!itemBool) {
            if ((item == "Earrings") || (item == "Footwear") || (item == "Glasses")) {
                when (item) {
                    "Earrings" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.earrings),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "Footwear" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.footwear),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "Glasses" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.glasses),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                }
            } else {
                if (numOfItems > 1) {
                    when (item) {
                        "Necklase" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.necklaces),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Coin" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.coins),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Clothing" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.clothings),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Watch" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.watches),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Wheelchair" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.wheelchairs),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                    }
                } else {
                    when (item) {
                        "Necklase" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.necklace),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Coin" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.coin),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Clothing" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.clothing),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Watch" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.watch),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Wheelchair" -> PlayAudio(
                            MediaPlayer.create(context, R.raw.wheelchair),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                    }
                }
            }
        } else if (!locationBool) {
            if (numOfItems > 1) {
                when (location) {
                    "Center" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.sentence_center),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerRight" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.sentence_lower_right),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerLeft" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.sentence_lower_left),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "UpperRight" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.sentence_upper_right),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "UpperLeft" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.sentence_upper_left),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                }
            } else {
                when (location) {
                    "Center" -> PlayAudio(
                        MediaPlayer.create(context, R.raw.one_sentence_center),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerRight" -> PlayAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_lower_right
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "LowerLeft" -> PlayAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_lower_left
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "UpperRight" -> PlayAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_upper_right
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "UpperLeft" -> PlayAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_upper_left
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                }
            }
        }
    }

    private fun PlayAudio(
        audio: MediaPlayer,
        numOfItems: Int,
        numOfItemsBool: Boolean,
        item: String,
        itemBool: Boolean,
        location: String,
        locationBool: Boolean
    ) {
        audio.setOnCompletionListener(OnCompletionListener {
            audio.stop()
            if (!numOfItemsBool) {
                StartPlayAudio(numOfItems, true, item, itemBool, location, locationBool)
            } else if (!itemBool) {
                StartPlayAudio(numOfItems, true, item, true, location, locationBool)
            } else if (!locationBool) {
                StartPlayAudio(numOfItems, true, item, true, location, true)
            }
        })
        audio.start()
    }
}