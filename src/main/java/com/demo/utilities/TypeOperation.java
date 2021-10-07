package com.demo.utilities;

public enum TypeOperation {

    VERSEMET("versement"),
    RETRAIT("retrait"),
    VIREMENT("virement");

    private String operation;
    private TypeOperation(String operation){
        this.operation = operation;
    }

}
