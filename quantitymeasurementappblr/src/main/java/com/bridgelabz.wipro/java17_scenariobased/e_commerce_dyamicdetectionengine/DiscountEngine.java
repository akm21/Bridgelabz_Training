package com.bridgelabz.wipro.java17_scenariobased.e_commerce_dyamicdetectionengine;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscountEngine {
    sealed interface Customer permits RegularCustomer, PrimeCustomer, NewCustomer {
    }

    static final class RegularCustomer implements Customer {
    }

    static final class PrimeCustomer implements Customer {
    }

    static final class NewCustomer implements Customer {
    }

    record Order(String orderId, Customer customer, double orderValue) {
    }

    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("01", new PrimeCustomer(), 12000),
                new Order("02", new RegularCustomer(), 6000),
                new Order("03", new NewCustomer(), 3000)
        );
        Map<String, Double> result = orders.stream().collect(Collectors.toMap(Order::orderId, DiscountEngine::calculateFinalAmount));
        result.forEach((id, amount) -> System.out.println("Order ID: " + id + " Final Amount: " + amount));
    }

    static double calculateFinalAmount(Order order) {
        double discount = switch (order.customer()) {
            case PrimeCustomer p ->
                    order.orderValue() > 10000 ? order.orderValue() * 0.15 : order.orderValue() > 5000 ? order.orderValue() * 0.10 : 0;
            case RegularCustomer r -> order.orderValue() > 5000 ? order.orderValue() * 0.05 : 0;
            case NewCustomer n -> 500;
        };
        return order.orderValue() - discount;
    }
}
