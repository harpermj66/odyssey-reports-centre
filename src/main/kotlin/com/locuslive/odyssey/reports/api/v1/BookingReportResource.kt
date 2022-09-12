package com.locuslive.odyssey.reports.api.v1

import com.locuslive.odyssey.reports.helper.ReportHelper
import com.locuslive.odyssey.reports.helper.ReportLocations
import com.locuslive.odyssey.reports.model.BookingList
import com.locuslive.odyssey.reports.service.BookingReportService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/run")
class BookingReportResource(val bookingReportService: BookingReportService) {

    @POST
    @Path("{reportName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun bookingList(@PathParam("reportName") reportName: String, @QueryParam("type") type: String, params: BookingList): Response {
        ReportHelper.errorIfInvalid(type, reportName)
        val byteArray = bookingReportService.bookingList(reportName, params, type.lowercase().trim(),
            ReportLocations.reportPath(reportName)!!)
        return ReportHelper.addAttachmentHeaders(type.lowercase(),reportName,byteArray)
    }

}