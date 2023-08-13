package tacos.domain.service;

import org.springframework.stereotype.Service;
import tacos.domain.repository.TacoOrderRepository;

/**
 * @Author sxs
 * @Date 2023/8/6 18:10
 */
@Service
public class OrderAdminService {

    private final TacoOrderRepository tacoOrderRepository;

    public OrderAdminService(TacoOrderRepository tacoOrderRepository) {
        this.tacoOrderRepository = tacoOrderRepository;
    }


    //    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteAllOrders() {
        tacoOrderRepository.deleteAll();
    }
}
