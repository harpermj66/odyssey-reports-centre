package com.locuslive.odyssey.reports.service

import com.locuslive.odyssey.reports.helper.ReportHelper
import com.locuslive.odyssey.reports.model.BookingList
import io.agroal.api.AgroalDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BookingReportService(val datasource: AgroalDataSource) {

    fun bookingList(reportName: String, parameters: BookingList, type: String, reportFileName: String): ByteArray {
        val reportFile = BookingReportService::class.java.getResource(reportFileName)!!
        val compiledReport = JasperCompileManager.compileReport(reportFile.openStream())
        val jasperPrint = JasperFillManager.fillReport(compiledReport, bookingListConvert(parameters), datasource.connection)
        return ReportHelper.convertPrint(type, jasperPrint)
    }

    private fun bookingListConvert(parameters: BookingList): Map<String, Any?> {
        val jParams = mutableMapOf<String,Any?>()
        jParams["USER_COMPANY_ID"] = parameters.userCompanyId
        jParams["USER_COMPANY_NAME"] = parameters.userCompanyName
        jParams["USER_COMPANY_AND_ADDRESS"] = parameters.userCompanyAndAddress
        jParams["USER_NAME"] = parameters.userName
        jParams["CARRIER_ID"] = parameters.carrierId
        jParams["VESSEL_NAME"] = parameters.vesselName
        jParams["FLAG"] = parameters.flag
        jParams["VOY_NUM"] = parameters.voyageNum
        jParams["DIS_VOY_NUM"] = parameters.displayVoyageNum
        jParams["LLOYDS_NR"] = parameters.lloydsNR
        jParams["LOAD_PORT_ID_MIN"] = parameters.loadPortIdMin
        jParams["LOAD_PORT_ID_MAX"] = parameters.loadPortIdMax
        jParams["LOAD_PORT_ETD"] = parameters.loadPortETD
        jParams["LOAD_PORT_ETA"] = parameters.loadPortETA
        jParams["DIS_PORT_ID_MIN"] = parameters.disPortIdMin
        jParams["DIS_PORT_ID_MAX"] = parameters.disPortIdMax
        jParams["DIS_PORT_ETD"] = parameters.disPortETD
        jParams["DIS_PORT_ETA"] = parameters.disPortETA
        jParams["VOYAGE_REF"] = parameters.voyageRef
        jParams["LOAD_PORT_NAME"] = parameters.loadPortName
        jParams["DISCHARGE_PORT_NAME"] = parameters.dischargePortName
        jParams["BP_ID"] = parameters.bpId
        jParams["BA_ID"] = parameters.baId
        jParams["LOAD_TERMINAL_ID"] = parameters.loadTerminalId
        jParams["DISCHARGE_TERMINAL_ID"] = parameters.dischargeTerminalId
        jParams["LOGO_IMAGE_ID"] = parameters.logoImageId.ifEmpty { null }
        jParams["CONTAINER_OPERATOR_ID"] = parameters.containerOperatorId
        jParams["BOOKING_STATUS_FILTER"] = parameters.bookingStatusFilter
        jParams["INCLUDE_REQUESTED_BOOKINGS"] = parameters.includeRequestedBookings
        if (parameters.showNonOperationalReefers) {
            jParams["SHOW_NON_OPERATIONAL_REEFERS"] = true
        }
        jParams["CONTAINER_OPERATOR_NAME"] = parameters.containerOperatorName
        return jParams
    }
}