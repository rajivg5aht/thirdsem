package com.example.ai36.view

fun main() {
//    println("hello world")

    //mutable variable
    var firstName = "sandis"
    var lastName = "prajapti"

    println(firstName + " " + lastName)

    println("${firstName.length} $lastName")

//    String name = "sandis"

    var name: String = "sandis"

    val age: Int = 10

//    var address = arrayOf("ktm","Pokhara","Bhaktapur")

//    address[3] = "sasa"
//
//    println(address[3])

    var data = ArrayList<Any>()

    data.add(1)
    data.add(2)
    data.add("ram")

    var address = arrayListOf("ktm", "chitwan")
    address.add("pokhara")


    var dictionary = mapOf(
        "Apple" to "This is fruit",
        "Samsung" to "This is brand"
    )

    println("The value of Apple is ${dictionary["Apple"]}")


//    for(int i = 0 ;i<5 ;i++){
//        println(i)
//    }



}

//public int calculate(int a, int b){
//
//}
//
//fun calculate(a: Int,b:Int) : Unit{
//
//}