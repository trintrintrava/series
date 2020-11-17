package com.example.series.series.logic;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<String> listStrings = new ArrayList<String>();

        listStrings.add("Hello0");
        listStrings.add("Hello1");
        listStrings.add("Hello2");
        listStrings.add("Hello3");
        listStrings.add("Hello4");

        for (String item:listStrings){
            System.out.println(item);
        }


        listStrings.forEach(item -> {
            System.out.println(item);
        });

        List<String> newList = listStrings.stream()
//                stream используется при нескольких независимых действиях
                .filter(item -> {
                    System.out.println();
                    return item.length() > 5;
                })
                .map(item -> {
                    System.out.println();
                    return item + "-" + item;
                })
                .filter(item -> item.contains("2"))
                .collect(Collectors.toList());

        newList.forEach(item -> {
            System.out.println(item);
        });

        List<Act2> actList = new ArrayList<>();

        List<String> result  = actList.stream()
                .filter(item -> item.age > 32)
                .map(item -> item.name)
                .collect(Collectors.toList());

    }

    class Act2 {
        private String name;
        private Integer age;
    }
}
