package com.typify.app.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import androidx.core.content.FileProvider
import com.typify.app.model.TestResult
import java.io.File
import java.io.FileOutputStream

object ShareCardGenerator {

    fun generateShareCard(context: Context, result: TestResult): Uri? {
        val width = 1080
        val height = 1920

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background — dark OLED
        val bgPaint = Paint().apply { color = 0xFF0F0F23.toInt() }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Gradient card
        val cardLeft = 80f
        val cardTop = 200f
        val cardRight = width - 80f
        val cardBottom = 1200f
        val cardRadius = 60f

        val gradient = LinearGradient(
            cardLeft, cardTop, cardRight, cardBottom,
            result.gradientStart.toInt(),
            result.gradientEnd.toInt(),
            Shader.TileMode.CLAMP
        )
        val cardPaint = Paint().apply {
            shader = gradient
            isAntiAlias = true
        }
        canvas.drawRoundRect(cardLeft, cardTop, cardRight, cardBottom, cardRadius, cardRadius, cardPaint)

        // Emoji
        val emojiPaint = Paint().apply {
            textSize = 180f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(result.emoji, width / 2f, cardTop + 220f, emojiPaint)

        // Type code (e.g., "INTJ")
        val typePaint = Paint().apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 160f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText(result.typeCode, width / 2f, cardTop + 420f, typePaint)

        // Title (e.g., "The Architect")
        val titlePaint = Paint().apply {
            color = 0xCCFFFFFF.toInt()
            textSize = 56f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
        canvas.drawText(result.title, width / 2f, cardTop + 500f, titlePaint)

        // Description (wrapped)
        val descPaint = Paint().apply {
            color = 0xD9FFFFFF.toInt()
            textSize = 40f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        val descLines = wrapText(result.description, 36)
        var descY = cardTop + 580f
        descLines.forEach { line ->
            canvas.drawText(line, width / 2f, descY, descPaint)
            descY += 56f
        }

        // Match percentage badge
        val matchPaint = Paint().apply {
            color = 0x33FFFFFF.toInt()
            isAntiAlias = true
        }
        val matchText = "${result.confidenceScore.toInt()}% match"
        val matchPaintText = Paint().apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 42f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val matchWidth = 350f
        val matchX = (width - matchWidth) / 2f
        val matchY = cardBottom - 120f
        canvas.drawRoundRect(matchX, matchY, matchX + matchWidth, matchY + 80f, 40f, 40f, matchPaint)
        canvas.drawText(matchText, width / 2f, matchY + 55f, matchPaintText)

        // "Typify" branding at bottom
        val brandPaint = Paint().apply {
            color = 0xFF7C3AED.toInt()
            textSize = 56f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        canvas.drawText("Typify", width / 2f, 1500f, brandPaint)

        val taglinePaint = Paint().apply {
            color = 0xFF6B6B80.toInt()
            textSize = 36f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("Find your personality type", width / 2f, 1560f, taglinePaint)

        // Dimension bars below card
        val barPaint = Paint().apply { color = 0xFF252540.toInt(); isAntiAlias = true }
        val barFillPaint = Paint().apply {
            isAntiAlias = true
            shader = LinearGradient(0f, 0f, width.toFloat(), 0f, 0xFF7C3AED.toInt(), 0xFFEC4899.toInt(), Shader.TileMode.CLAMP)
        }
        val labelPaint = Paint().apply {
            color = 0xFFA0A0B8.toInt()
            textSize = 32f
            isAntiAlias = true
        }
        val pctPaint = Paint().apply {
            color = 0xFFA0A0B8.toInt()
            textSize = 32f
            textAlign = Paint.Align.RIGHT
            isAntiAlias = true
        }

        var barY = 1660f
        result.dimensionBreakdown.forEach { (dim, confidence) ->
            val label = when(dim) {
                "EI" -> "E ↔ I"
                "SN" -> "S ↔ N"
                "TF" -> "T ↔ F"
                "JP" -> "J ↔ P"
                else -> dim
            }
            canvas.drawText(label, 80f, barY, labelPaint)
            canvas.drawText("${confidence.toInt()}%", width - 80f, barY, pctPaint)
            barY += 20f
            canvas.drawRoundRect(80f, barY, width - 80f, barY + 12f, 6f, 6f, barPaint)
            val fillWidth = (width - 160f) * (confidence / 100f)
            canvas.drawRoundRect(80f, barY, 80f + fillWidth, barY + 12f, 6f, 6f, barFillPaint)
            barY += 60f
        }

        // Save bitmap to cache
        return saveBitmap(context, bitmap)
    }

    private fun wrapText(text: String, maxChars: Int): List<String> {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var current = StringBuilder()

        for (word in words) {
            if (current.length + word.length + 1 > maxChars) {
                lines.add(current.toString())
                current = StringBuilder(word)
            } else {
                if (current.isNotEmpty()) current.append(" ")
                current.append(word)
            }
        }
        if (current.isNotEmpty()) lines.add(current.toString())
        return lines
    }

    private fun saveBitmap(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val cacheDir = File(context.cacheDir, "share_images")
            cacheDir.mkdirs()
            val file = File(cacheDir, "typify_share_${System.currentTimeMillis()}.png")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            bitmap.recycle()
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createShareIntent(context: Context, uri: Uri, text: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}
