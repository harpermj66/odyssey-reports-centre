package com.locuslive.odyssey.reports.helper

class ReportLocations {

    companion object {

        private const val BOOKING_LIST_CONTAINER = "bookingListContainer"
        private const val BOOKING_LIST_CONTAINER_FILENAME = "/booking/BookingListContainer.jrxml"

        private val reportPaths = mapOf(
            BOOKING_LIST_CONTAINER to BOOKING_LIST_CONTAINER_FILENAME
        )

        fun reportPath(reportName: String): String? {
            return reportPaths[reportName]
        }
    }
}