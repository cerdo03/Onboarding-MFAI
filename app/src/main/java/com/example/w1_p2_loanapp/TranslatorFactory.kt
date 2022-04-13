package com.example.w1_p2_loanapp

import android.app.Activity


class TranslatorFactory private constructor() {

    enum class TRANSLATORS {
        TEXT_TO_SPEECH,
        SPEECH_TO_TEXT
    }

    interface IConverter {
        fun initialize(message: String, appContext: Activity): IConverter

        fun getErrorText(errorCode: Int): String
    }


    fun with(TRANSLATORS: TRANSLATORS, conversionCallback: ConversionCallback): IConverter {
        return SpeechToTextConverter(conversionCallback)
    }

    companion object {
        val instance = TranslatorFactory()
    }
}