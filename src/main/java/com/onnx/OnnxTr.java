package com.onnx;

import com.utils.*;
import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfocr.OcrPdfCreator;
import com.itextpdf.pdfocr.onnxtr.OnnxTrOcrEngine;
import com.itextpdf.pdfocr.onnxtr.detection.IDetectionPredictor;
import com.itextpdf.pdfocr.onnxtr.detection.OnnxDetectionPredictor;
import com.itextpdf.pdfocr.onnxtr.orientation.IOrientationPredictor;
import com.itextpdf.pdfocr.onnxtr.orientation.OnnxOrientationPredictor;
import com.itextpdf.pdfocr.onnxtr.recognition.IRecognitionPredictor;
import com.itextpdf.pdfocr.onnxtr.recognition.OnnxRecognitionPredictor;

import com.utils.Utils;

import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;


public class OnnxTr {
    public static final String DEST = Utils.createGenericPath("result", "onnx_tr_example");
    private static final String ROTATED_IMAGE = Utils.createGenericPath("img", "rotated.png");
    private static final String BASIC_IMAGE = Utils.createGenericPath("img", "ocrExample.png");

    private static final String FAST = Utils.createGenericPath("model", "rep_fast_tiny-28867779.onnx"); 
    private static final String CRNNVGG16 = Utils.createGenericPath("model", "crnn_vgg16_bn-662979cc.onnx");
    private static final String MOBILENETV3 = Utils.createGenericPath("model", "mobilenet_v3_small_crop_orientation-5620cf7e.onnx");

        public static void main( String[] args )
    {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        try {
            new OnnxTr().manipulate(DEST);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    protected void manipulate(String destination) throws Exception {
        List<File> images = Arrays.asList(new File(BASIC_IMAGE), new File(ROTATED_IMAGE));

        IDetectionPredictor detectionPredictor = OnnxDetectionPredictor.fast(FAST);
        IOrientationPredictor orientationPredictor = OnnxOrientationPredictor.mobileNetV3(MOBILENETV3);
        IRecognitionPredictor recognitionPredictor = OnnxRecognitionPredictor.crnnVgg16(CRNNVGG16);

        try (OnnxTrOcrEngine ocrEngine =
                     new OnnxTrOcrEngine(detectionPredictor, orientationPredictor, recognitionPredictor);
             OutputStream output = FileUtil.getFileOutputStream(destination)) {
            OcrPdfCreator pdfCreator = new OcrPdfCreator(ocrEngine);
            pdfCreator.createPdf(images, new PdfWriter(output)).close();
        }
    }
}
