package com.locuslive.odyssey.reports.model

import java.util.Date

class BookingList {
    var userCompanyId = -1L
    var userCompanyName = ""
    var userCompanyAndAddress = ""
    var userName = ""
    var carrierId = -1L
    var vesselName = ""
    var flag = ""
    var voyageNum: String? = null
    var displayVoyageNum = ""
    var lloydsNR = ""
    var loadPortIdMin = -1L
    var loadPortIdMax = -1L
    var loadPortETD: Date = Date()
    var loadPortETA: Date = Date()
    var disPortIdMin = -1L
    var disPortIdMax = -1L
    var disPortETD: Date = Date()
    var disPortETA: Date = Date()
    var voyageRef = -1L
    var loadPortName = ""
    var dischargePortName = ""
    var bpId = -1L
    var baId = -1L
    var loadTerminalId = -1L
    var dischargeTerminalId = -1L
    var logoImageId: String = ""
    var containerOperatorId = -1L
    var bookingStatusFilter = ""
    var includeRequestedBookings = false
    var showNonOperationalReefers: Boolean = false
    var containerOperatorName = ""
}