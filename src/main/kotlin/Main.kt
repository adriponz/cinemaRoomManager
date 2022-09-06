var rows: Int = 0
var seats: Int = 0
var scheme: MutableList<MutableList<Char>> = ArrayList()

fun getScheme() {
    for (i in 1..rows) {
        var list: MutableList<Char> = ArrayList()
        for (j in 1..seats) {
            list.add('S')
        }
        scheme.add(list)
    }
}

fun printScheme() {
    println("Cinema:")
    print(" ")
    for (i in 1..seats) {
        print(" " + i)
    }
    println()
    for (i in 1..rows) {
        println("$i " + scheme[i-1].joinToString(" "))
    }
}

fun calculateTotalIncome(): Int {
    return if (rows * seats <= 60) {
        rows * seats * 10
    } else  if (rows * seats % 2 == 0) {
        ((rows / 2) * seats * 10) + ((rows / 2) * seats * 8)
    } else {
        ((rows / 2) * seats * 10) + (((rows / 2) + 1) * seats * 8)
    }
}

fun calculateSeatPrice(row: Int): Int {
    return if (rows * seats <= 60) {
        10
    } else  if (row <= (rows / 2)) {
        10
    } else  {
        8
    }
}

fun calculateCurrentIncome(): Int {
    var income = 0
    for (i in 0 until rows) {
        for (j in 0 until seats) {
            if (scheme[i][j] == 'B') {
                income += calculateSeatPrice(i + 1)
            }
        }
    }
    return income
}

fun calculateNumberPurchasedTickets(): Int {
    var numberPurchasedTickets = 0
    for (i in 0 until rows) {
        for (j in 0 until seats) {
            if (scheme[i][j] == 'B') {
                numberPurchasedTickets++
            }
        }
    }
    return numberPurchasedTickets
}

fun calculatePercentage(numberPurchasedTickets: Int): String {
    val percentage = (numberPurchasedTickets.toDouble() / (rows * seats)) * 100
    return "%.2f".format(percentage)
}

fun updateScheme(row: Int, seat: Int) {
    scheme[row-1][seat-1] = 'B'
}

fun isSeatAvailable(row: Int, seat: Int): Boolean = scheme[row][seat] == 'S'

fun isSeatOutOfBounds(row: Int, seat: Int): Boolean = row > rows || seat > seats

fun buyTicket() {

    var seatPurchased = false
    while (!seatPurchased) {
        println("Enter a row number:")
        val row = readln().toInt()
        println("Enter a seat number in that row:")
        val seat = readln().toInt()

        seatPurchased = if (isSeatOutOfBounds(row, seat)) {
            println("Wrong input!")
            false
        } else if (!isSeatAvailable(row-1, seat-1)) {
            println("That ticket has already been purchased!")
            false
        } else {
            val price = calculateSeatPrice(row)
            println("Ticket price: $" + price)
            updateScheme(row, seat)
            true
        }
    }

}

fun showStatistics() {
    val numberPurchasedTickets = calculateNumberPurchasedTickets()
    println("Number of purchased tickets: " + numberPurchasedTickets)
    println("Percentage: " + calculatePercentage(numberPurchasedTickets) + "%")
    println("Current income: $" + calculateCurrentIncome())
    println("Total income: $" + calculateTotalIncome())
}

fun menu() {
    while (true) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val option = readln().toInt()
        when (option) {
            1 -> printScheme()
            2 -> buyTicket()
            3 -> showStatistics()
            0 -> return
        }
    }
}

fun main() {
    println("Enter the number of rows:")
    rows = readln().toInt()
    println("Enter the number of seats in each row:")
    seats = readln().toInt()

    getScheme()

    menu()
}