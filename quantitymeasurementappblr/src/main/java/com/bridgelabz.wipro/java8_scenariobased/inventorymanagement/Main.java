package com.bridgelabz.wipro.java8_scenariobased.inventorymanagement;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<Product> products = Arrays.asList(
            new Product("P1", "Electronics", 5, 50000),
            new Product("P2", "Electronics", 20, 15000),
            new Product("P3", "Grocery", 200, 100),
            new Product("P4", "Grocery", 50, 200));

    public static void main(String[] args) {

        Map<String, Double> totalledPerCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(p -> p.getPrice() * p.getQuantity())));

        totalledPerCategory.forEach((category, totalValue) -> System.out.println(category + " : " + totalValue));


        List<Product> lowStockProducts = products.stream()
                .filter(p -> p.getQuantity() < 10)
                .toList();
        lowStockProducts.forEach(System.out::println);

        Map<String, Optional<Product>> highestPricedProductPerCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.maxBy(Comparator.comparing(Product::getPrice))));

        highestPricedProductPerCategory.forEach((category, product) -> System.out.println(category + " : " + product.orElse(null)));

        double totalInventoryValue = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();
        System.out.println("Total Inventory Value: " + totalInventoryValue);
    }

}