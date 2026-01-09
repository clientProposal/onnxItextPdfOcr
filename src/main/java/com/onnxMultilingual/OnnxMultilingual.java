package com.onnxMultilingual;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfocr.OcrPdfCreator;
import com.itextpdf.pdfocr.OcrPdfCreatorProperties;
import com.itextpdf.pdfocr.onnxtr.OnnxTrOcrEngine;
import com.itextpdf.pdfocr.onnxtr.detection.IDetectionPredictor;
import com.itextpdf.pdfocr.onnxtr.detection.OnnxDetectionPredictor;
import com.itextpdf.pdfocr.onnxtr.orientation.IOrientationPredictor;
import com.itextpdf.pdfocr.onnxtr.orientation.OnnxOrientationPredictor;
import com.itextpdf.pdfocr.onnxtr.recognition.IRecognitionPredictor;
import com.itextpdf.pdfocr.onnxtr.recognition.OnnxRecognitionPredictor;
import com.itextpdf.pdfocr.onnxtr.recognition.Vocabulary;
import com.utils.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OnnxMultilingual {
    public static final String DEST_FINNISH = Utils.createGenericPath("result", "onnx_multilingual_example_finnish");
    public static final String DEST_POLISH = Utils.createGenericPath("result", "onnx_multilingual_example_polish");
    private static final String FINNISH = Utils.createGenericPath("img", "finnish.png");
    private static final String POLISH = Utils.createGenericPath("img", "polish.png");
    private static final String FAST = Utils.createGenericPath("model", "rep_fast_tiny-28867779.onnx"); 
    private static final String MOBILENETV3 = Utils.createGenericPath("model", "mobilenet_v3_small_crop_orientation-5620cf7e.onnx");
    private static final String MULTILANG = Utils.createGenericPath("model", "onnxtr-parseq-multilingual-v1.onnx");

    public static void main( String[] args ) {
        File finnishFile = new File(DEST_FINNISH);
        File polishFile = new File(DEST_POLISH);

        finnishFile.getParentFile().mkdirs();
        polishFile.getParentFile().mkdirs();

        try {
            new OnnxMultilingual().manipulate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    protected void manipulate() throws Exception {
        List<File> imagesFinnish = Arrays.asList(new File(FINNISH));
        List<File> imagesPolish = Arrays.asList(new File(POLISH));

        IDetectionPredictor detectionPredictor = OnnxDetectionPredictor.fast(FAST);
        IOrientationPredictor orientationPredictor = OnnxOrientationPredictor.mobileNetV3(MOBILENETV3);

        IRecognitionPredictor recognitionPredictor =
                OnnxRecognitionPredictor
                .parSeq(MULTILANG, Vocabulary.LATIN_EXTENDED, 0);
        

        try (OnnxTrOcrEngine ocrEngine =
                     new OnnxTrOcrEngine(detectionPredictor, orientationPredictor, recognitionPredictor)) {

            OcrPdfCreatorProperties ocrPdfCreatorProperties = new OcrPdfCreatorProperties()
                    .setTextLayerName("OnnxTR multilingual example");

            // // Set green text color to show the recognition result. Skip that step for real usages.
            // OcrPdfCreatorProperties ocrPdfCreatorProperties = new OcrPdfCreatorProperties()
            //         .setTextLayerName("OnnxTR multilingual example")
            //         .setTextColor(ColorConstants.GREEN);


            OcrPdfCreator pdfCreator = new OcrPdfCreator(ocrEngine, ocrPdfCreatorProperties);
            pdfCreator.createPdf(imagesFinnish, new PdfWriter(DEST_FINNISH)).close();
            pdfCreator.createPdf(imagesPolish, new PdfWriter(DEST_POLISH)).close();

        }
    }

}
