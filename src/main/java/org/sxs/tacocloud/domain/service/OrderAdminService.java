package org.sxs.tacocloud.domain.service;

import org.springframework.stereotype.Service;
import org.sxs.tacocloud.domain.repository.OrderRepository;

/**
 * @Author sxs
 * @Date 2023/8/6 18:10
 */
@Service
public class OrderAdminService {

    private final OrderRepository orderRepository;

    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    //    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
