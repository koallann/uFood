package br.ufal.ic.ufood.presentation.order

import br.ufal.ic.ufood.data.order.OrderRepositoryImpl
import br.ufal.ic.ufood.domain.Order
import br.ufal.ic.ufood.presentation.shared.LABEL_TYPE_OPTION
import br.ufal.ic.ufood.presentation.shared.MSG_INVALID_OPTION
import br.ufal.ic.ufood.presentation.shared.extensions.formatToString
import br.ufal.ic.ufood.presentation.shared.mvp.BasicConsole

class OrdersConsole : BasicConsole(), OrdersView {

    private val presenter: OrdersPresenter by lazy {
        OrdersPresenter(OrderRepositoryImpl())
    }

    override fun start() {
        super.start()
        presenter.attachView(this)
        showMenu()
    }

    override fun stop() {
        super.stop()
        presenter.detachView()
    }

    override fun showOrders(orders: List<Order>) {
        println(LABEL_ORDERS)
        orders.forEachIndexed { index, order ->
            println(String.format(LABEL_ORDER_DETAILS, index, order.createdAt.formatToString("dd/MM/yyyy, hh:mm")))
            order.items.forEach {
                println(String.format(LABEL_ORDER_ITEM, it.first.name, it.second))
            }
            println(String.format(LABEL_ORDER_PRICE, order.price))
        }
        holdOutput()
        showMenu()
    }

    override fun requestOrderId(): Int {
        print(LABEL_TYPE_ORDER_ID)
        return scanner.nextLine().toInt()
    }

    override fun onOrderDeliveredConfirmation() {
        println(MSG_ORDER_DELIVERED)
        holdOutput()
        showMenu()
    }

    override fun onError(message: String) {
        println("An error occurred: $message")
        holdOutput()
        showMenu()
    }

    private fun showMenu() {
        println("ORDERS\n")
        println("1 - Show pending orders")
        println("2 - Show complete orders")
        println("3 - Confirm an order delivery")
        println("0 - Back")
        print(LABEL_TYPE_OPTION)

        when (scanner.nextLine().toInt()) {
            1 -> presenter.onLoadPendingOrders()
            2 -> presenter.onLoadCompleteOrders()
            3 -> presenter.onConfirmOrderDelivery()
            0 -> {
                holdOutput()
                stop()
            }
            else -> {
                println(MSG_INVALID_OPTION)
                holdOutput()
                showMenu()
            }
        }
    }

}