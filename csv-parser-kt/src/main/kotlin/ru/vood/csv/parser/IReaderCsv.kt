package ru.vood.csv.parser

import kotlinx.coroutines.flow.Flow

interface IReaderCsv {

    fun <T : ICSVLine> readCsv(
        stringFlow: Flow<String>,
        csvEntityTemplate: AbstractCsvEntityTemplate<T>,
        delimiter: String = ","
    ): Flow<T>
}