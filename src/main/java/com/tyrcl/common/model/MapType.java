package com.tyrcl.common.model;

public enum MapType {
    DOMINATION("domination"),
    CONQUEST("conquest");

    private final String type;

   // Constructor
   MapType(String type) {
        this.type = type;
    }

   // Method to get the card type
    public String getType() {
        return type;
    }
}