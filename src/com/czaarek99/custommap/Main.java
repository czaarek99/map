package com.czaarek99.custommap;

public class Main {
    public static void main(String[] args) {
        var map = new CustomMap<String, Integer>();

        map.put("bruh1", 1);
        map.put("bruh1", 2);
        map.put("bruh1", 3);
        map.put("bruh1", 4);
        map.put("bruh1", 5);

        System.out.println(map.get("bruh1"));
    }
}
