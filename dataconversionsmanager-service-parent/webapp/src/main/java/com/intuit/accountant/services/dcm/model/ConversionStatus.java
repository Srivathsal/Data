package com.intuit.accountant.services.dcm.model;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ConversionStatus {

    private int NumberOfInputFiles;
    private int ConvertedFiles;
    private int DemoFiles;
    private boolean Success;
    private int ExtractionFailedCount;
    private int ConversionFailedCount;
    private String Message;
    private float ProgressPercentage;
    private int ErrorCode;

    public String getMessage() {
        return Message;
    }

    @JsonSetter("Message")
    public void setMessage(String message) {
        this.Message = message;
    }

    public Integer getNumberOfInputFiles() {
        return NumberOfInputFiles;
    }

    @JsonSetter("NumberOfInputFiles")
    public void setNumberOfInputFiles(int numberOfInputFiles) {
        NumberOfInputFiles = numberOfInputFiles;
    }

    public int getConvertedFiles() {
        return ConvertedFiles;
    }

    @JsonSetter("ConvertedFiles")
    public void setConvertedFiles(int convertedFiles) {
        ConvertedFiles = convertedFiles;
    }

    public int getDemoFiles() {
        return DemoFiles;
    }

    @JsonSetter("DemoFiles")
    public void setDemoFiles(int demoFiles) {
        DemoFiles = demoFiles;
    }

    public boolean isSuccess() {
        return Success;
    }

    @JsonSetter("Success")
    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getExtractionFailedCount() {
        return ExtractionFailedCount;
    }

    @JsonSetter("ExtractionFailedCount")
    public void setExtractionFailedCount(int extractionFailedCount) {
        ExtractionFailedCount = extractionFailedCount;
    }

    public int getConversionFailedCount() {
        return ConversionFailedCount;
    }

    @JsonSetter("ConversionFailedCount")
    public void setConversionFailedCount(int conversionFailedCount) {
        ConversionFailedCount = conversionFailedCount;
    }

    public float getProgressPercentage() {
        return ProgressPercentage;
    }

    @JsonSetter("ProgressPercentage")
    public void setProgressPercentage(float progressPercentage) {
        ProgressPercentage = progressPercentage;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    @JsonSetter("ErrorCode")
    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }
}
