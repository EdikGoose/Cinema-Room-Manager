package cinema

fun main() {
    println("Enter the number of rows:")
    val numberOfRows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val numberOfColumns = readLine()!!.toInt()

    val seats = MutableList(numberOfRows){ MutableList(numberOfColumns) { "S" }}

    while(true){
        printMenu()
        when(readLine()!!.toInt()){
            1 -> printSeats(seats)
            2 -> buyTicket(seats)
            3 -> printStat(seats)
            0 -> break
            else -> println("Invalid number")
        }
    }
}

fun printMenu() = println("""
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
""".trimIndent()
)

fun printSeats(seats: MutableList<MutableList<String>>){
    val numberOfRows = seats.size
    val numberOfColumns = seats.first().size

    println("Cinema:")
    print(" ")
    for(i in 1..numberOfColumns){
        print(" $i")
    }
    println()
    for(i in 0 until numberOfRows){
        print("${i+1} ")
        for(j in 0 until numberOfColumns){
            print("${seats[i][j]} ")
        }
        println()
    }
}

fun buyTicket(seats: MutableList<MutableList<String>>){
    println("Enter a row number:")
    val rowNumber = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val columnNumber = readLine()!!.toInt()

    if (rowNumber !in 1..seats.size || columnNumber !in 1..seats.first().size) {
        println("Wrong input!")
        buyTicket(seats)
        return
    }

    if (seats[rowNumber-1][columnNumber-1] != "S") {
        println("That ticket has already been purchased!")
        buyTicket(seats)
        return
    }


    seats[rowNumber-1][columnNumber-1] = "B"

    val numberOfRows = seats.size
    val numberOfColumns = seats.first().size

    val price = getPriceOfTicket(numberOfRows, numberOfColumns, rowNumber)
    println("Ticket price: \$$price")
}

fun printStat(seats: MutableList<MutableList<String>>){
    val numberOfPurchased = calculatePurchased(seats)
    val income = calculateCurrentIncome(seats)

    println("""
        Number of purchased tickets: $numberOfPurchased
        Percentage: ${String.format("%.2f", (numberOfPurchased.toDouble() / (seats.size * seats.first().size).toDouble())*100)+"%"}
        Current income: $${income}
        Total income: $${calculateTotalIncome(seats)}""".trimIndent())
}

fun calculateCurrentIncome(seats: MutableList<MutableList<String>>): Int{
    var income = 0
    for(indexOfRow in 0 until seats.size){
        for(indexOfColumn in 0 until seats.first().size){
            if (seats[indexOfRow][indexOfColumn] == "B") {
                income += getPriceOfTicket(seats.size, seats.first().size, indexOfRow+1)
            }
        }
    }
    return income
}

fun calculateTotalIncome(seats: MutableList<MutableList<String>>): Int{
    var income = 0
    for(indexOfRow in 0 until seats.size){
        for(indexOfColumn in 0 until seats.first().size){
            income += getPriceOfTicket(seats.size, seats.first().size, indexOfRow+1)
        }
    }
    return income
}

fun calculatePurchased(seats: MutableList<MutableList<String>>): Int{
    var numberOfPurchased = 0
    for (row in seats) {
        for(seat in row){
            if (seat == "B") {
                numberOfPurchased++
            }
        }
    }
    return numberOfPurchased
}

fun getPriceOfTicket(numberOfRows: Int, numberOfColumns: Int, rowNumber: Int) = if(numberOfRows * numberOfColumns <= 60) {
    10
} else{
    if(rowNumber <= numberOfRows / 2){ 10 } else{ 8 }
}
