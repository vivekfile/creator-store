package org.vaya.creatorstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaya.creatorstore.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
