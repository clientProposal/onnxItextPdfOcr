package com.utils;

public class Utils {

    public static String repositoryPath = System.getProperty("user.dir") + "/";
    public static String resourcesPath = repositoryPath + "src/main/resources/";
    public static String modelPath = resourcesPath + "model/";
    public static String imagePath = resourcesPath + "img/";
    public static String pdfsPath = resourcesPath + "pdf/";
    public static String outputPath = resourcesPath + "output/";

    public static String createGenericPath(String type, String specifier) {
        switch(type){
            case "result": {
                return outputPath + "result_of_" + specifier + ".pdf";
            }
            case "img": {
                return imagePath + specifier;
            }
            case "model": {
                return modelPath + specifier;
            }
            default: {
                return "c";
            }
        }
    } 
        public static void main( String[] args )
    {
        System.out.println(System.getProperty("user.dir"));
    }
}
