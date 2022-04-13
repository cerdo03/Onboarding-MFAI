package com.example.w1_p2_loanapp

interface ConversionCallback {

    fun onSuccess(result: String)

    fun onCompletion()

    fun onErrorOccurred(errorMessage: String)

}