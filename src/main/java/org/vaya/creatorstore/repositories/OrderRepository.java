package org.vaya.creatorstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaya.creatorstore.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
