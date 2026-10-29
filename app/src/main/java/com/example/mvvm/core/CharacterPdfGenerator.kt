package com.example.mvvm.core

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import com.example.mvvm.domain.model.CharacterModel
import java.io.OutputStream
import javax.inject.Inject

class CharacterPdfGenerator {

    fun generate(
        characters: List<CharacterModel>,
        outputStream: OutputStream
    ) {

        val pdfDocument = PdfDocument()

        try {

            val pageWidth = 595
            val pageHeight = 842

            val marginLeft = 45f
            val marginRight = 45f
            val marginTop = 50f
            val marginBottom = 50f

            val titlePaint = Paint().apply {
                color = Color.rgb(
                    23,
                    32,
                    38
                )

                textSize = 24f

                isFakeBoldText = true
            }

            val subtitlePaint = Paint().apply {

                color = Color.rgb(
                    46,
                    125,
                    50
                )

                textSize = 13f

                isFakeBoldText = true
            }

            val namePaint = Paint().apply {

                color = Color.rgb(
                    23,
                    32,
                    38
                )

                textSize = 17f

                isFakeBoldText = true
            }

            val normalPaint = Paint().apply {

                color = Color.rgb(
                    90,
                    96,
                    100
                )

                textSize = 11f
            }

            val separatorPaint = Paint().apply {

                color = Color.rgb(
                    220,
                    225,
                    220
                )

                strokeWidth = 1f
            }

            var pageNumber = 1

            var pageInfo =
                PdfDocument.PageInfo
                    .Builder(
                        pageWidth,
                        pageHeight,
                        pageNumber
                    )
                    .create()

            var page =
                pdfDocument.startPage(
                    pageInfo
                )

            var canvas =
                page.canvas

            var y = marginTop

            canvas.drawText(
                "Rick & Morty Multiverso",
                marginLeft,
                y,
                titlePaint
            )

            y += 25f

            canvas.drawText(
                "Listado de personajes",
                marginLeft,
                y,
                subtitlePaint
            )

            y += 22f

            canvas.drawText(
                "Total exportado: ${characters.size}",
                marginLeft,
                y,
                normalPaint
            )

            y += 35f

            characters.forEachIndexed {
                    index,
                    character ->

                if (
                    y > pageHeight -
                    marginBottom -
                    135f
                ) {

                    pdfDocument.finishPage(
                        page
                    )

                    pageNumber++

                    pageInfo =
                        PdfDocument.PageInfo
                            .Builder(
                                pageWidth,
                                pageHeight,
                                pageNumber
                            )
                            .create()

                    page =
                        pdfDocument.startPage(
                            pageInfo
                        )

                    canvas =
                        page.canvas

                    y = marginTop

                    canvas.drawText(
                        "Rick & Morty Multiverso",
                        marginLeft,
                        y,
                        subtitlePaint
                    )

                    y += 30f
                }

                canvas.drawText(
                    "${index + 1}. ${character.name}",
                    marginLeft,
                    y,
                    namePaint
                )

                y += 20f

                canvas.drawText(
                    "Estado: ${character.status}",
                    marginLeft + 12f,
                    y,
                    normalPaint
                )

                y += 16f

                canvas.drawText(
                    "Especie: ${character.species}",
                    marginLeft + 12f,
                    y,
                    normalPaint
                )

                y += 16f

                canvas.drawText(
                    "Género: ${character.gender}",
                    marginLeft + 12f,
                    y,
                    normalPaint
                )

                y += 16f

                canvas.drawText(
                    "Origen: ${shortText(character.origin)}",
                    marginLeft + 12f,
                    y,
                    normalPaint
                )

                y += 16f

                canvas.drawText(
                    "Ubicación: ${shortText(character.location)}",
                    marginLeft + 12f,
                    y,
                    normalPaint
                )

                y += 18f

                canvas.drawLine(
                    marginLeft,
                    y,
                    pageWidth - marginRight,
                    y,
                    separatorPaint
                )

                y += 24f
            }

            pdfDocument.finishPage(
                page
            )

            pdfDocument.writeTo(
                outputStream
            )

            outputStream.flush()

        } finally {

            pdfDocument.close()
        }
    }

    private fun shortText(
        text: String,
        maxLength: Int = 65
    ): String {

        if (
            text.length <= maxLength
        ) {
            return text
        }

        return text
            .take(
                maxLength
            ) + "..."
    }
}