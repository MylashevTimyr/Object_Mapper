package org.example.object_mapper.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.model.Order;
import org.example.object_mapper.model.Product;
import org.example.object_mapper.repository.CustomerRepository;
import org.example.object_mapper.repository.OrderRepository;
import org.example.object_mapper.repository.ProductRepository;
import org.example.object_mapper.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Order createOrder(Order order) {
        // Проверяем и загружаем клиента
        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + order.getCustomer().getCustomerId() + " not found"));

        // Загружаем продукты из базы данных
        Map<Long, Integer> productsWithQuantities = order.getProductsWithQuantities().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), // Ключ остается Long, как в Map<Long, Integer>
                        entry -> {
                            // Проверяем, что продукт существует
                            productRepository.findById(entry.getKey())
                                    .orElseThrow(() -> new EntityNotFoundException("Product with ID " + entry.getKey() + " not found"));
                            return entry.getValue(); // Возвращаем количество
                        }
                ));

        // Рассчитываем общую стоимость заказа
        BigDecimal totalPrice = productsWithQuantities.entrySet().stream()
                .map(entry -> {
                    Product product = productRepository.findById(entry.getKey())
                            .orElseThrow(() -> new EntityNotFoundException("Product with ID " + entry.getKey() + " not found"));
                    Integer quantity = entry.getValue();
                    if (product.getPrice() == null) {
                        throw new IllegalArgumentException("Price of product with ID " + product.getProductId() + " is null");
                    }
                    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Заполняем поля заказа
        order.setCustomer(customer);
        order.setProductsWithQuantities(productsWithQuantities); // Типы теперь совпадают (Map<Long, Integer>)
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Pending");

        // Сохраняем заказ
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }
}


