package Utils

import java.time.LocalDateTime
import java.sql.Date

class DateUtils {
    companion object {
        fun localDateTimeToSqlDate(localDateTime: LocalDateTime): Date {
            return Date.valueOf(localDateTime.toLocalDate())
        }
    }
}
