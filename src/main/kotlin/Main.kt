fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val mutSeats = MutableList(rows) {MutableList(seats){'S'}}

    var choice: Int
    do {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        choice = readln().toInt()

        when(choice){
            1 -> displaySeats(mutSeats)
            2 -> buyTicket(mutSeats)
            3 -> showStats(mutSeats)
        }

    } while(choice != 0)
}

fun displaySeats(mutSeats: MutableList<MutableList<Char>>){
    val topRow = mutableListOf<Char>()
    topRow.add(' ')
    for(i in 1..mutSeats[0].size)
        topRow.add(i.digitToChar())

    println("\nCinema:")
    println(topRow.joinToString(" "))

    for(j in 0 until mutSeats.size)
        println("${j+1} ${mutSeats[j].joinToString( " ")}")
}

fun buyTicket(mutSeats: MutableList<MutableList<Char>>) {
    var complete = false
    do{
        println("\nEnter a row number:")
        val targetRow = readln().toInt()
        println("Enter a seat number in that row:")
        val targetSeat = readln().toInt()

        try{
            if(mutSeats[targetRow-1][targetSeat-1] == 'S'){
                println("\nTicket price: $${getAmount(targetRow, mutSeats)}")

                mutSeats[targetRow-1][targetSeat-1] = 'B'
                complete = true
            }
            else
                println("\nThat ticket has already been purchased")
        }
        catch (e: IndexOutOfBoundsException){
            println("\nWrong input!")
        }
    } while(!complete)
}

fun showStats(mutSeats: MutableList<MutableList<Char>>) {
    var ticket = 0
    var income = 0

    for(i in 0 until mutSeats.size){
        for(j in 0 until mutSeats[0].size){
            if(mutSeats[i][j]=='B'){
                ticket += 1
                income += getAmount(i+1,mutSeats)
            }
        }
    }

    println("\nNumber of purchased tickets: $ticket")
    println("Percentage: %.2f%%".format(ticket/(mutSeats.size*mutSeats[0].size).toDouble()*100.0))
    println("Current income: $$income")
    println("Total income: $${getMaxAmount(mutSeats)}")
}

val getAmount: (Int, MutableList<MutableList<Char>>) -> Int = {targetRow: Int, mutSeats: MutableList<MutableList<Char>> ->
    when{
        mutSeats.size * mutSeats[0].size < 60 -> 10
        targetRow <= mutSeats.size / 2 -> 10
        else -> 8
    }
}

val getMaxAmount: (MutableList<MutableList<Char>>) -> Int = {mutSeats: MutableList<MutableList<Char>> ->
    val totalSeats = mutSeats.size * mutSeats[0].size
    if(totalSeats < 60)
        totalSeats * 10
    else {
        val frontRows = mutSeats.size / 2
        val backRows = frontRows + mutSeats.size % 2
        (10*frontRows*mutSeats[0].size)+(8*backRows*mutSeats[0].size)
    }
}