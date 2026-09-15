package org.vaya.creatorstore.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaya.creatorstore.dto.OrderItemRequest;
import org.vaya.creatorstore.dto.OrderRequest;
import org.vaya.creatorstore.entities.Order;
import org.vaya.creatorstore.entities.OrderItem;
import org.vaya.creatorstore.entities.Product;
import org.vaya.creatorstore.repositories.OrderRepository;
import org.vaya.creatorstore.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest){
        List<OrderItem> orderItems = new ArrayList<>();
    BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = new Order();
        order.setCustomerName(order.getCustomerName());
        order.setCustomerEmail(order.getCustomerEmail());
        order.setStatus("Confirmed");

        for (OrderItemRequest itemRequest : orderRequest.getItems()){
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found " + itemRequest.getProductId()
                    ));

            //Check the product stock
            if(product.getStockQuantity() < itemRequest.getQuantity()){
                throw new RuntimeException("Not enough stock for "+ itemRequest.getProductId());
            }

            //calculate the total price

            BigDecimal priceOfItem = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(priceOfItem);

            //update the product table with latest stock quantity
            product.setStockQuantity(
                    product.getStockQuantity() - itemRequest.getQuantity()
            );
            productRepository.save(product);

            //Builder patter to make obj
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItems.add(orderItem);

        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        return order;
    }
}
