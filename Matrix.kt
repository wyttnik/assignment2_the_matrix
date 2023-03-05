@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

import java.lang.IllegalArgumentException

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl(height,width, e)
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(override val height: Int,
                    override val width: Int,
                    e:E) : Matrix<E> {

    private val elements = MutableList(height*width) {e}

    override fun get(row: Int, column: Int): E = elements[width*row+column]

    override fun get(cell: Cell): E = get(cell.row,cell.column)

    override fun set(row: Int, column: Int, value: E) {
        elements[width*row+column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row,cell.column, value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is Matrix<*>) return false
        if (height != other.height || width != other.width) return false
        for(i in 0 until height)
            for(j in 0 until width)
                if (this[i,j] != other[i, j])
                    return false
        return true
    }

    override fun toString(): String {
        var printMatrix = ""
        for(i in 0 until height) {
            for (j in 0 until width)
                printMatrix += "${this[i, j]}\t"
            printMatrix += '\n'
        }
        return printMatrix
    }
}
