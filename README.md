__Before using__


run.sh can be used to run this project, changing classes as required

Regarding onnxMultilingual:

```java
        IRecognitionPredictor recognitionPredictor =
                OnnxRecognitionPredictor
                .parSeq(MULTILANG, Vocabulary.LATIN_EXTENDED, 0);
```

This model will always use Vocabulary.LATIN_EXTENDED (see its config.json on hugging face for further details).

"Vocabulary" is used to map text recognition model results (integer index continuous from 0) to characters, so it should contain characters corresponding to model output vector of probability-like values,
e.g. model probabilities output is [0.05, 0.91, 0.04], vocabulary is [a, b, c] => recognized symbol is b,
so "Vocabulary" should be the part of the model properties.

This model will be appropriate for a wide range of left-to-right languages.

This AI assistant can help with iText:
https://deepwiki.com/itext/itext-pdfocr-java

You can also override the model’s output processing:

com.itextpdf.pdfocr.onnxtr.recognition.IRecognitionPostProcessor#process

__Training this model__

1. Check the tools in the docTR project for model training (detection, recognition)
https://github.com/mindee/doctr/tree/main/references/detection
https://github.com/mindee/doctr/tree/main/references/recognition
2. Train the models with the use of the supported architectures/vocabularies
3. Export it to the ONNX format:
https://mindee.github.io/doctr/using_doctr/using_model_export.html#export-to-onnx

