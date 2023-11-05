
import com.collect.invest.dao.TransactionsDao
import com.collect.invest.dao.WalletsDao
import com.collect.invest.plugins.controllers.financialServiceControllers.transactionController
import com.collect.invest.plugins.controllers.financialServiceControllers.walletController
import io.ktor.server.routing.*

fun Route.financialRoutes(walletsDao: WalletsDao, transactionsDao: TransactionsDao) {

        // Маршруты для кошельков
        route("/wallet") {
            walletController(walletsDao)
        }

        // Маршруты для транзакций
        route("/transaction") {
            transactionController(transactionsDao)
        }
}
