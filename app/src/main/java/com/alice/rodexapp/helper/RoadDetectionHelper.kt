package com.alice.rodexapp.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage

class ObjectDetectorClassifier(context: Context) {
    private val interpreter: Interpreter
    private val inputSize = 640  // Ensure this matches the model's expected input size

    init {
        val model = FileUtil.loadMappedFile(context, "rdd_pro_40.tflite")
        interpreter = Interpreter(model)
    }

    data class DetectionResult(val classId: Int, val score: Float, val boundingBox: RectF)

    fun detect(bitmap: Bitmap): List<DetectionResult> {
        // Resize bitmap to match model input size
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)

        // Convert bitmap to TensorImage
        val tensorImage = TensorImage.fromBitmap(resizedBitmap)

        // Prepare input buffer
        val inputBuffer = tensorImage.buffer.rewind()

        // Prepare output buffer
        val outputBuffer = Array(1) { Array(8400) { FloatArray(8) } }

        // Run the model
        interpreter.run(inputBuffer, outputBuffer)

        // Process the output
        val results = mutableListOf<DetectionResult>()
        for (i in 0 until 8400) {
            val detection = outputBuffer[0][i]
            val score = detection[4]
            if (score > 0.5) {
                val classId = detection[5].toInt()
                val boundingBox = RectF(
                    detection[0] * bitmap.width / inputSize,  // Scale back to original image size
                    detection[1] * bitmap.height / inputSize,
                    detection[2] * bitmap.width / inputSize,
                    detection[3] * bitmap.height / inputSize
                )
                results.add(DetectionResult(classId, score, boundingBox))
            }
        }
        return results
    }
}
