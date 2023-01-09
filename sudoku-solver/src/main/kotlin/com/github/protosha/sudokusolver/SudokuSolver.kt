package com.github.protosha.sudokusolver

import java.security.InvalidParameterException
import javax.naming.LimitExceededException
import kotlin.random.Random

val sudokuDigits = setOf('1', '2', '3', '4', '5', '6', '7', '8', '9')

object SudokuSolver {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "[[\".\",\".\",\"9\",\"7\",\"4\",\"8\",\".\",\".\",\".\"],[\"7\",\".\",\".\",\".\",\".\",\".\",\".\",\".\",\".\"],[\".\",\"2\",\".\",\"1\",\".\",\"9\",\".\",\".\",\".\"],[\".\",\".\",\"7\",\".\",\".\",\".\",\"2\",\"4\",\".\"],[\".\",\"6\",\"4\",\".\",\"1\",\".\",\"5\",\"9\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\"3\",\".\",\".\"],[\".\",\".\",\".\",\"8\",\".\",\"3\",\".\",\"2\",\".\"],[\".\",\".\",\".\",\".\",\".\",\".\",\".\",\".\",\"6\"],[\".\",\".\",\".\",\"2\",\"7\",\"5\",\"9\",\".\",\".\"]]"
        val chars = input.toCharArray().filter { it !in setOf('[', ']', '"', ',')}
        val board = chars.chunked(9).map { it.toCharArray() }.toTypedArray()
        println("[${board.joinToString(",") { "[${it.joinToString(",") { c -> "\"$c\"" }}]" }}]")
        Solution().solveSudoku(board)
        println("[${board.joinToString(",") { "[${it.joinToString(",") { c -> "\"$c\"" }}]" }}]")
    }
}

class Solution {
    companion object {
        val sudokuValues = setOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '.')
        const val MAX_ITERATIONS = 100000
    }

    fun solveSudoku(board: Array<CharArray>): Unit {
        val sudokuBoard = createBoard(board)
        val fills = ArrayList<Fill>(100)
        var iterations = 0
        var lastMultipleFill: Fill? = null
        while (!sudokuBoard.isFilled() && iterations < MAX_ITERATIONS) {
            sudokuBoard.draft()
            if (sudokuBoard.hasEmpties()) {
                if (lastMultipleFill != null) {
                    while (fills.last() != lastMultipleFill) {
                        val fill = fills.removeAt(fills.lastIndex)
                        fill.cell.values = fill.fromValues
                        fill.cell.isFilled = false
                    }
                    val filledValue = setOf((lastMultipleFill.fromValues - lastMultipleFill.filledValues).random())
                    lastMultipleFill.filledValues = lastMultipleFill.filledValues + filledValue
                    lastMultipleFill.cell.values = filledValue
                } else {
                    throw InvalidParameterException("Last multiple fill is empty - possibly invalid sudoku board")
                }
            } else if (sudokuBoard.hasSingles()) {
                fills.addAll(sudokuBoard.fillSingles())
            } else {
                lastMultipleFill = sudokuBoard.fillBestMultiple()
                fills.add(lastMultipleFill!!)
            }
            iterations++
        }

        if (!sudokuBoard.isFilled()) {
            throw LimitExceededException("Unable to find sudoku solution - max iterations exceeded")
        }
        sudokuBoard.syncTo(board)
//        println("[${sudokuBoard.toInputBoard().joinToString(",") { "[${it.joinToString(",") { c -> "\"$c\"" }}]" }}]")
    }

    private fun createBoard(board: Array<CharArray>): SudokuBoard {
        val tempBoard = board.mapIndexed { i, row ->
            row.mapIndexed { j, value -> Triple(i, j, value) }
        }
        if (tempBoard.size != 9) {
            throw InvalidParameterException("Sudoku board must have exactly 9 rows, got ${tempBoard.size} rows")
        }
        tempBoard.forEachIndexed { i, row ->
            if (row.size != 9) throw InvalidParameterException("Sudoku board must have exactly 9 cells in a row, got ${row.size} in row ${i + 1}")
        }
        tempBoard.flatten()
            .forEach {
                if (it.third !in sudokuValues) throw InvalidParameterException("""
                    Sudoku cells must only contain (${sudokuValues.joinToString()}), got ${it.third} in cell [${it.first}, ${it.second}]
                """.trimIndent())
            }

        val rows = (0..8).map { SudokuRow(it) }
        val columns = (0..8).map { SudokuColumn(it) }
        val boxes = (0..2).map { i -> (0..2).map { j -> SudokuBox(i * 3, j * 3) } }
            .flatten()
        val cells = tempBoard.flatten().map {
            val row = rows.find { row -> row.contains(it.first, it.second) }
            val column = columns.find { column -> column.contains(it.first, it.second) }
            val box = boxes.find { box -> box.contains(it.first, it.second) }
            val cell = Cell(
                i = it.first,
                j = it.second,
                values = if (it.third == '.') setOf() else setOf(it.third),
                row = row!!,
                column = column!!,
                box = box!!,
                isFilled = it.third != '.'
            )
            row.cells.add(cell)
            column.cells.add(cell)
            box.cells.add(cell)
            return@map cell
        }
        return SudokuBoard(cells, rows, columns, boxes)
    }
}

class SudokuBoard(
    val cells: List<Cell>,
    val rows: List<SudokuRow>,
    val columns: List<SudokuColumn>,
    val boxes: List<SudokuBox>
) {
    fun draft() {
        for (cell in cells) {
            if (cell.isFilled) continue
            cell.values = sudokuDigits - cell.row.presentDigits() - cell.column.presentDigits() - cell.box.presentDigits()
        }
    }

    fun fillSingles(): List<Fill> {
        val fills = ArrayList<Fill>(10)
        for (cell in cells) {
            if (cell.isFilled || cell.values.size != 1) continue
            cell.isFilled = true
            fills.add(Fill(cell, cell.values, cell.values))
        }
        return fills
    }

    fun fillBestMultiple(): Fill? {
        var bestMultiple: Cell? = null
        for (cell in cells) {
            if (cell.isFilled || cell.values.size <= 1) continue
            if (bestMultiple == null || bestMultiple.values.size > cell.values.size) {
                bestMultiple = cell
            }
        }

        if (bestMultiple != null) {
            val filledValue = setOf(bestMultiple.values.random())
            val fill = Fill(bestMultiple, bestMultiple.values, filledValue)
            bestMultiple.values = filledValue
            bestMultiple.isFilled = true
            return fill
        } else {
            return null
        }
    }

    fun hasSingles() = cells.find { it.values.size == 1 } != null
    fun hasEmpties() = cells.find { it.values.size == 0 } != null

    fun isFilled() = cells.find { !it.isFilled } == null

    fun toInputBoard(): Array<CharArray> {
        val board = mutableListOf<CharArray>()
        for (row in rows.sortedBy { it.i }) {
            board.add(
                row.cells.sortedBy { it.j }
                    .map { if (it.isFilled) it.values.first() else '.' }
                    .toCharArray()
            )
        }
        return board.toTypedArray()
    }

    fun syncTo(board: Array<CharArray>) {
        val inputBoard = toInputBoard()
        inputBoard.forEachIndexed { i, row -> row.forEachIndexed { j, c -> board[i][j] = c } }
    }
}

abstract class SudokuArea {
    open val cells = ArrayList<Cell>(9)

    // TODO: can be optimized by rewriting to for cycle
    fun presentDigits() = cells.filter { it.values.size == 1 }
        .map { it.values.first() }
        .toSet()

    // TODO: can be optimized by rewriting to for cycle
    fun missingDigits() = (sudokuDigits - presentDigits())

    abstract fun contains(i: Int, j: Int): Boolean
}

class SudokuBox(val i: Int, val j: Int) : SudokuArea() {
    override fun contains(i: Int, j: Int) = i >= this.i && i < this.i + 3 && j >= this.j && j < this.j + 3
}
class SudokuRow(val i: Int): SudokuArea() {
    override fun contains(i: Int, j: Int) = i == this.i
}
class SudokuColumn(val j: Int): SudokuArea() {
    override fun contains(i: Int, j: Int) = j == this.j
}

class Cell(
    val i: Int,
    val j: Int,
    var values: Set<Char>,
    val row: SudokuRow,
    val column: SudokuColumn,
    val box: SudokuBox,
    var isFilled: Boolean
)

class Fill(
    val cell: Cell,
    val fromValues: Set<Char>,
    var filledValues: Set<Char>
)
