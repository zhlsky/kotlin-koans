package iii_conventions

import java.util.*


data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    operator fun plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.ti, timeIntervals.n)
    operator fun plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)

    override fun compareTo(other: MyDate): Int {
        if (this.year != other.year) return (this.year - other.year)
        if (this.month != other.month) return (this.month - other.month)
        return (this.dayOfMonth - other.dayOfMonth)
    }
}



operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int) = RepeatedTimeInterval(this, n)

data class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : Iterable<MyDate> , ClosedRange<MyDate> {
    override fun contains(d: MyDate): Boolean = d >= start && d <= endInclusive

    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current: MyDate = start

        override fun next(): MyDate {
            if (!hasNext()) {
                throw NoSuchElementException()
            }

            val result = current
            current = current.nextDay()
            return result
        }

        override fun hasNext(): Boolean = current <= endInclusive
    }
}
