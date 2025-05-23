package ru.vood.csv.parser

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

abstract class AbstractCsvEntityTemplate<T : ICSVLine> {

    abstract fun toEntity(strValues: List<String>): T

    abstract val header: String
    abstract val delimiter: String
    val mapHeaderWithIndex: Map<FiledName, Int> by lazy {
        header.split(delimiter)
            .withIndex()
            .associate { FiledName(it.value) to it.index }
    }

    private inline fun <reified T> convert(
        field: IFieldConstants,
        mapHeaderWithIndex: Map<FiledName, Int>,
        strValues: List<String>
    ): T = kotlin.runCatching {
        val key = field.filedName
        val s = strValues[mapHeaderWithIndex.getValue(key)]
        val convertStr = convertStr<T>(s)
        convertStr
    }.getOrElse { error("asd") }

    private inline fun <reified T> convertStr(fieldValue: String): T {
        return if (fieldValue == "" || fieldValue == "NULL") {
            null as T
        } else {
            when (T::class) {
                Short::class -> fieldValue.toShort() as T
                Int::class -> fieldValue.toInt() as T
                Long::class -> fieldValue.toLong() as T
                Float::class -> fieldValue.toFloat() as T
                Double::class -> fieldValue.toDouble() as T
                String::class -> fieldValue as T
                Boolean::class -> fieldValue.toBoolean() as T
                Instant::class -> Instant.now() as T
                LocalDate::class -> LocalDate.now() as T
                LocalDateTime::class -> LocalDateTime.now() as T
                else -> error("Unsupported type ${T::class.java.canonicalName}")
            }
        }
    }
}