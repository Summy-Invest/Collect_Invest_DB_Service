package Utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.sql.Date

public class DateUtils {



    companion object {
        fun localDateTimeToSqlDate(localDateTime: LocalDateTime): Date {
            return Date.valueOf(localDateTime.toLocalDate())
        }
    }

}
