package br.ufal.ic.ufood.domain.coupon

import br.ufal.ic.ufood.domain.Restaurant
import java.util.*

abstract class Coupon(val discount: Int) {

    abstract fun isValid(restaurant: Restaurant): Boolean

    protected fun getTodayService(restaurant: Restaurant): Restaurant.ServiceDay? {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_WEEK)
        val todayService = restaurant.serviceDays.find { it.day == today }

        return todayService?.let {
            if (getHourOfDay() in it.start..it.end) {
                it
            } else {
                null
            }
        }
    }

    protected fun getHourOfDay(): Int {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

}