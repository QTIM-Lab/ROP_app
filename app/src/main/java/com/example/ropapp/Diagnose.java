package com.example.ropapp;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Trace;

import org.tensorflow.Operation;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.lang.reflect.Array;



public class Diagnose
{
    float[] results = new float[3];
    private TensorFlowInferenceInterface inferenceInterface;
    private String inputName = "input_1_1";
    private String outputName = "pred0";
    private String[] outputNames;
    private AssetManager assetManager;
    private int inputSize = 224;
    private int imageMean = 117;
    private int imageStd = 1;
    private int[] intValues;
    private float[] floatValues;
    private int numClasses;
    private float[] outputs;

    public Diagnose(AssetManager assetManager, final Bitmap bitmap)
    {
        this.assetManager = assetManager;
        inferenceInterface = new TensorFlowInferenceInterface(assetManager, "file:///android_asset/tf_model.pb");
        intValues = new int[inputSize * inputSize];
        floatValues = new float[inputSize * inputSize * 3];

        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            floatValues[i * 3 + 0] = (((val >> 16) & 0xFF) - imageMean) / imageStd;
            floatValues[i * 3 + 1] = (((val >> 8) & 0xFF) - imageMean) / imageStd;
            floatValues[i * 3 + 2] = ((val & 0xFF) - imageMean) / imageStd;
        }

        final Operation operation = inferenceInterface.graphOperation(outputName);
        numClasses = (int) operation.output(0).shape().size(1);

        outputs = new float[numClasses];
        outputNames = new String[] {outputName};
    }

    public float[] tensor()
    {
        // Copy the input data into TensorFlow.
        Trace.beginSection("feed");
        inferenceInterface.feed(inputName, floatValues, 1, inputSize, inputSize, 3);
        Trace.endSection();

        // Run the inference call.
        Trace.beginSection("run");
        inferenceInterface.run(outputNames, false);
        Trace.endSection();

        // Copy the output Tensor back into the output array.
        Trace.beginSection("fetch");
        inferenceInterface.fetch(outputName, outputs);
        Trace.endSection();

        for(int i = 0; i < outputs.length; i++)
            results[i] = outputs[i];

        return results;
    }
}
