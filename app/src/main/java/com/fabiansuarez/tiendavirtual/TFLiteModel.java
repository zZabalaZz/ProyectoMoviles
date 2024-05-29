package com.fabiansuarez.tiendavirtual;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class TFLiteModel {

    private static final int INPUT_SIZE = 224; // Ajusta según tu modelo
    private static final int NUM_BYTES_PER_CHANNEL = 4; // Usualmente 4 bytes para Float
    private Interpreter interpreter;
    private List<String> labelList;

    public TFLiteModel(Context context) {
        try {
            interpreter = new Interpreter(loadModelFile(context, "model.tflite"));
            labelList = loadLabelList(context, "label.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer loadModelFile(Context context, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength).order(ByteOrder.nativeOrder());
    }

    private List<String> loadLabelList(Context context, String labelPath) throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(labelPath)));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    public String analyzeImage(Bitmap bitmap) {
        Bitmap resizedBitmap = getResizedBitmap(bitmap, INPUT_SIZE, INPUT_SIZE);
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(resizedBitmap);

        float[][] result = new float[1][labelList.size()]; // Ajusta según la salida de tu modelo
        interpreter.run(byteBuffer, result);

        return getMaxResult(result[0]);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(INPUT_SIZE * INPUT_SIZE * 3 * NUM_BYTES_PER_CHANNEL);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < INPUT_SIZE; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                byteBuffer.putFloat((val & 0xFF) / 255.0f);
            }
        }
        return byteBuffer;
    }

    private String getMaxResult(float[] result) {
        int maxIndex = -1;
        float maxValue = -1;
        for (int i = 0; i < result.length; i++) {
            if (result[i] > maxValue) {
                maxValue = result[i];
                maxIndex = i;
            }
        }
        return labelList.get(maxIndex);
    }
}
