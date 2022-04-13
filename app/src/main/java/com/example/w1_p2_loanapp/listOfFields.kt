package com.example.w1_p2_loanapp


class listOfFields {
    fun getList():ArrayList<field>{
        val arr=ArrayList<field>()
        arr.add(field("Name","text"))
        arr.add(field("Age","number"))
        arr.add(field("Gender","text"))
        arr.add(field("Phone No.","phone"))
        arr.add(field("Address","text"))
        arr.add(field("Annual Income","number"))
        arr.add(field("Loan Amount","number"))
        arr.add(field("Purpose of Loan","text"))
        return arr
    }
}