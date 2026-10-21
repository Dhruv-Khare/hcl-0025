
package Assignment2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class OrderSalesPipeline {

    record OrderLine(String product, String category, int qty,
                     BigDecimal unitPrice) {

        BigDecimal total() {
            return unitPrice.multiply(BigDecimal.valueOf(qty));
        }
    }

    record Order(int id, String customer, LocalDate date,
                 String status, List<OrderLine> lines) {

        BigDecimal total() {
            return lines.stream()
                    .map(OrderLine::total)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    static OrderLine l(String p, String c, int q, int price) {
        return new OrderLine(p, c, q, BigDecimal.valueOf(price));
    }

    public static void main(String[] args) {

        List<Order> orders = List.of(
                new Order(1, "Asha", LocalDate.parse("2026-01-05"),
                        "DELIVERED",
                        List.of(
                                l("Laptop", "Electronics", 1, 50000),
                                l("Mouse", "Electronics", 2, 500)
                        )
                ),
                new Order(2, "Ravi", LocalDate.parse("2026-01-12"),
                        "DELIVERED",
                        List.of(
                                l("Book", "Books", 3, 400),
                                l("Pen", "Stationery", 10, 20)
                        )
                ),
                new Order(3, "Asha", LocalDate.parse("2026-02-03"),
                        "CANCELLED",
                        List.of(l("Phone", "Electronics", 1, 30000))
                ),
                new Order(4, "Meera", LocalDate.parse("2026-02-10"),
                        "DELIVERED",
                        List.of(
                                l("Book", "Books", 2, 400),
                                l("Laptop", "Electronics", 1, 50000)
                        )
                ),
                new Order(5, "Ravi", LocalDate.parse("2026-02-18"),
                        "DELIVERED",
                        List.of(
                                l("Mouse", "Electronics", 1, 500),
                                l("Pen", "Stationery", 5, 20)
                        )
                )
        );

        // 1. Total revenue from delivered orders
        BigDecimal revenue = orders.stream()
                .filter(o -> o.status().equals("DELIVERED"))
                .map(Order::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("1. Total Delivered Revenue: " + revenue);
//
//        // 2. Group orders by status
//        Map<String, List<Order>> ordersByStatus = orders.stream()
//                .collect(Collectors.groupingBy(Order::status));
//
//        System.out.println("\n2. Orders By Status:");
//        ordersByStatus.forEach((status, list) ->
//                System.out.println(status + " = " + list.size()));
//
//        // 3. Total sales for each customer (delivered orders only)
//        Map<String, BigDecimal> salesByCustomer = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .collect(Collectors.groupingBy(
//                        Order::customer,
//                        Collectors.reducing(
//                                BigDecimal.ZERO,
//                                Order::total,
//                                BigDecimal::add
//                        )
//                ));
//
//        System.out.println("\n3. Sales By Customer:");
//        salesByCustomer.forEach((customer, total) ->
//                System.out.println(customer + " = " + total));
//
//        // 4. Total quantity sold for each product
//        Map<String, Integer> quantityByProduct = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .flatMap(o -> o.lines().stream())
//                .collect(Collectors.groupingBy(
//                        OrderLine::product,
//                        Collectors.summingInt(OrderLine::qty)
//                ));
//
//        System.out.println("\n4. Quantity Sold By Product:");
//        quantityByProduct.forEach((product, qty) ->
//                System.out.println(product + " = " + qty));
//
//        // 5. Revenue by category
//        Map<String, BigDecimal> revenueByCategory = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .flatMap(o -> o.lines().stream())
//                .collect(Collectors.groupingBy(
//                        OrderLine::category,
//                        Collectors.reducing(
//                                BigDecimal.ZERO,
//                                OrderLine::total,
//                                BigDecimal::add
//                        )
//                ));
//
//        System.out.println("\n5. Revenue By Category:");
//        revenueByCategory.forEach((category, total) ->
//                System.out.println(category + " = " + total));
//
//        // 6. Find the highest-value delivered order
//        Optional<Order> highestOrder = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .max(Comparator.comparing(Order::total));
//
//        System.out.println("\n6. Highest-Value Delivered Order:");
//        highestOrder.ifPresent(o ->
//                System.out.println(
//                        "Order ID: " + o.id()
//                                + ", Customer: " + o.customer()
//                                + ", Total: " + o.total()
//                ));
//
//        // 7. Sort delivered orders by total in descending order
//        List<Order> sortedOrders = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .sorted(Comparator.comparing(Order::total).reversed())
//                .toList();
//
//        System.out.println("\n7. Delivered Orders Sorted By Total:");
//        sortedOrders.forEach(o ->
//                System.out.println(
//                        "Order " + o.id()
//                                + " | " + o.customer()
//                                + " | " + o.total()
//                ));
//
//        // 8. Count delivered orders per customer
//        Map<String, Long> orderCountByCustomer = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .collect(Collectors.groupingBy(
//                        Order::customer,
//                        Collectors.counting()
//                ));
//
//        System.out.println("\n8. Delivered Order Count By Customer:");
//        orderCountByCustomer.forEach((customer, count) ->
//                System.out.println(customer + " = " + count));
//
//        // 9. Average delivered order value
//        double averageOrderValue = orders.stream()
//                .filter(o -> o.status().equals("DELIVERED"))
//                .map(Order::total)
//                .mapToDouble(BigDecimal::doubleValue)
//                .average()
//                .orElse(0.0);
//
//        System.out.printf(
//                Locale.ROOT,
//                "%n9. Average Delivered Order Value: %.2f%n",
//                averageOrderValue
//        );
//
//        // 10. Products grouped by category
//        Map<String, Set<String>> productsByCategory = orders.stream()
//                .flatMap(o -> o.lines().stream())
//                .collect(Collectors.groupingBy(
//                        OrderLine::category,
//                        Collectors.mapping(
//                                OrderLine::product,
//                                Collectors.toCollection(TreeSet::new)
//                        )
//                ));
//
//        System.out.println("\n10. Products By Category:");
//        productsByCategory.forEach((category, products) ->
//                System.out.println(category + " = " + products));
    }
}
