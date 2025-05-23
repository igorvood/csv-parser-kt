package ru.vood.csv.parser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform

class ReaderCsvImpl : IReaderCsv {

    private val dispatcher = Dispatchers.IO.limitedParallelism(10)

    override fun <T : ICSVLine> readCsv(
        stringFlow: Flow<String>,
        csvEntityTemplate: AbstractCsvEntityTemplate<T>,
        delimiter: String
    ): Flow<T> = stringFlow
        .flowOn(dispatcher)
        .filterNot { it.isBlank() || it.replace(delimiter, "").isBlank() }
        .transform { string ->
            emit(csvEntityTemplate.toEntity(string.split(delimiter)))
        }
}