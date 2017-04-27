package com.devng.spark

import com.owlike.genson.Context
import com.owlike.genson.Converter
import com.owlike.genson.GensonBuilder
import com.owlike.genson.JsonBindingException
import com.owlike.genson.stream.JsonStreamException
import com.owlike.genson.stream.ObjectReader
import com.owlike.genson.stream.ObjectWriter
import spark.ResponseTransformer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object JsonUtil {

    private val genson = GensonBuilder()
            .useIndentation(true)
            .withConverter(LocalDateConverter(), LocalDate::class.java)
            .create()

    fun toJson(`object`: Any): String {
        return genson.serialize(`object`)
    }

    fun <T> fromJson(jsonString: String, type: Class<T>): T {
        try {
            val e = genson.deserialize(jsonString, type)
            return e
        } catch (ex: JsonStreamException) {
            throw IllegalArgumentException("Could not parse JSON: " + jsonString + ". Reason: " + ex.message)
        } catch (ex: JsonBindingException) {
            throw IllegalArgumentException("Could not parse JSON: " + jsonString + ". Reason: " + ex.message)
        }

    }

    fun json(): ResponseTransformer {
        return ResponseTransformer { toJson(it) }
    }

    private class LocalDateConverter : Converter<LocalDate> {

        @Throws(Exception::class)
        override fun serialize(localDate: LocalDate?, objectWriter: ObjectWriter, context: Context) {
            if (localDate == null) {
                return
            }
            objectWriter.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
        }

        @Throws(Exception::class)
        override fun deserialize(objectReader: ObjectReader, context: Context): LocalDate? {

            val dateString = objectReader.valueAsString()
            if (dateString == null || dateString.isEmpty()) {
                return null
            }
            try {
                return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (ex: DateTimeParseException) {
                throw IllegalArgumentException(ex)
            }

        }
    }

}
