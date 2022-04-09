package com.example.w1_p2_loanapp

import android.location.Address

class listOfFields {
    fun getList():ArrayList<field>{
        val arr=ArrayList<field>()
        arr.add(field("Name"))
        arr.add(field("Age"))
        arr.add(field("Gender"))
        arr.add(field("Phone No."))
        arr.add(field("Address"))
        arr.add(field("Annual Income"))
        arr.add(field("Loan Amount"))
        return arr
    }
}