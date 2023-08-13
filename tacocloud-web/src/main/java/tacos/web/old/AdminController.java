package tacos.web.old;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.domain.service.OrderAdminService;

/**
 * @Author sxs
 * @Date 2023/8/6 18:14
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderAdminService orderAdminService;

    public AdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @PostMapping("/deleteOrders")
    public String deleteAllOrders() {
        orderAdminService.deleteAllOrders();
        log.info("deleted all orders successfully");
        return "redirect:/admin";
    }
}
