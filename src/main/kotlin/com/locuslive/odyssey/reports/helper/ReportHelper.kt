package com.locuslive.odyssey.reports.helper

import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.export.HtmlExporter
import net.sf.jasperreports.engine.export.JRCsvExporter
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleHtmlExporterOutput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleWriterExporterOutput
import java.io.ByteArrayOutputStream
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
class ReportHelper {

    companion object {

        fun errorIfInvalid(type: String, name: String) {
            val cleanType = type.lowercase().trim()
            if (cleanType != "pdf" && cleanType != "csv" && cleanType != "html") {
                throw BadRequestException("Invalid Type")
            }
            if (ReportLocations.reportPath(name) == null) {
                throw NotFoundException("Report not found")
            }
        }

        fun addAttachmentHeaders(type: String, name: String, output: ByteArray): Response {
            var substituteName = name.replace(" ".toRegex(), "_")
            val response = Response.ok(output)
            response.header("Content-Disposition", "inline;filename=$name")
            when (type) {
                "html" -> {}
                "csv" -> {
                    response.header("Context-Type", MediaType.APPLICATION_OCTET_STREAM)
                    substituteName = "$substituteName.csv"
                }
                "pdf" -> {
                    response.header("Context-Type", MediaType.APPLICATION_OCTET_STREAM)
                    substituteName = "$substituteName.pdf"
                }
            }
            response.header(name, substituteName)
            response.header("Cache-Control", "must-revalidate, post-check=0, pre-check=0")
            return response.build()
        }

        fun convertPrint(type: String, jasperPrint: JasperPrint): ByteArray {
            val out = ByteArrayOutputStream()
            val exporter = when(type) {
                "html" -> {
                    HtmlExporter()
                }
                "csv" -> {
                    JRCsvExporter()
                }
                else -> {
                    JRPdfExporter()
                }
            }

            when (type) {
                "html" -> {
                    exporter.exporterOutput = SimpleHtmlExporterOutput(out)
                }
                "csv" -> {
                    exporter.exporterOutput = SimpleWriterExporterOutput(out)
                }
                else -> {
                    exporter.exporterOutput = SimpleOutputStreamExporterOutput(out)
                }
            }
            if (type != "pdf") {
                exporter.setExporterInput(SimpleExporterInput(jasperPrint))
                exporter.exportReport()
            } else {
                JasperExportManager.exportReportToPdfStream(jasperPrint, out)
            }

            return out.toByteArray()
        }
    }


 }