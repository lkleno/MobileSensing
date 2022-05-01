package io.github.lkleno.mobilesensing.audio

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import io.github.lkleno.mobilesensing.MainActivity
import io.github.lkleno.mobilesensing.R

class Audio(private var context: MainActivity){

    private fun startPlayAudio(
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
                    2 -> playAudio(
                        MediaPlayer.create(context, R.raw.two),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    3 -> playAudio(
                        MediaPlayer.create(context, R.raw.three),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    4 -> playAudio(
                        MediaPlayer.create(context, R.raw.four),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    5 -> playAudio(
                        MediaPlayer.create(context, R.raw.five),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    6 -> playAudio(
                        MediaPlayer.create(context, R.raw.six),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    7 -> playAudio(
                        MediaPlayer.create(context, R.raw.seven),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    8 -> playAudio(
                        MediaPlayer.create(context, R.raw.eight),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    9 -> playAudio(
                        MediaPlayer.create(context, R.raw.nine),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    10 -> playAudio(
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
                startPlayAudio(numOfItems, true, item, itemBool, location, locationBool)
            }
        } else if (!itemBool) {
            if ((item == "Earrings") || (item == "Footwear") || (item == "Glasses")) {
                when (item) {
                    "Earrings" -> playAudio(
                        MediaPlayer.create(context, R.raw.earrings),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "Footwear" -> playAudio(
                        MediaPlayer.create(context, R.raw.footwear),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "Glasses" -> playAudio(
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
                        "Necklase" -> playAudio(
                            MediaPlayer.create(context, R.raw.necklaces),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Coin" -> playAudio(
                            MediaPlayer.create(context, R.raw.coins),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Clothing" -> playAudio(
                            MediaPlayer.create(context, R.raw.clothings),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Watch" -> playAudio(
                            MediaPlayer.create(context, R.raw.watches),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Wheelchair" -> playAudio(
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
                        "Necklase" -> playAudio(
                            MediaPlayer.create(context, R.raw.necklace),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Coin" -> playAudio(
                            MediaPlayer.create(context, R.raw.coin),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Clothing" -> playAudio(
                            MediaPlayer.create(context, R.raw.clothing),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Watch" -> playAudio(
                            MediaPlayer.create(context, R.raw.watch),
                            numOfItems,
                            numOfItemsBool,
                            item,
                            itemBool,
                            location,
                            locationBool
                        )
                        "Wheelchair" -> playAudio(
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
                    "Center" -> playAudio(
                        MediaPlayer.create(context, R.raw.sentence_center),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerRight" -> playAudio(
                        MediaPlayer.create(context, R.raw.sentence_lower_right),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerLeft" -> playAudio(
                        MediaPlayer.create(context, R.raw.sentence_lower_left),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "UpperRight" -> playAudio(
                        MediaPlayer.create(context, R.raw.sentence_upper_right),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "UpperLeft" -> playAudio(
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
                    "Center" -> playAudio(
                        MediaPlayer.create(context, R.raw.one_sentence_center),
                        numOfItems,
                        numOfItemsBool,
                        item,
                        itemBool,
                        location,
                        locationBool
                    )
                    "LowerRight" -> playAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_lower_right
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "LowerLeft" -> playAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_lower_left
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "UpperRight" -> playAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_upper_right
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                    "UpperLeft" -> playAudio(
                        MediaPlayer.create(
                            context,
                            R.raw.one_sentence_upper_left
                        ), numOfItems, numOfItemsBool, item, itemBool, location, locationBool
                    )
                }
            }
        }
    }

    private fun playAudio(
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
                startPlayAudio(numOfItems, true, item, itemBool, location, locationBool)
            } else if (!itemBool) {
                startPlayAudio(numOfItems, true, item, true, location, locationBool)
            } else if (!locationBool) {
                startPlayAudio(numOfItems, true, item, true, location, true)
            }
        })
        audio.start()
    }
}