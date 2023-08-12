package tacos.web.old;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.sxs.tacocloud.config.OrderProps;
import org.sxs.tacocloud.domain.entity.TacoOrder;
import org.sxs.tacocloud.domain.entity.User;
import org.sxs.tacocloud.domain.repository.TacoOrderRepository;

/**
 * @Author sxs
 * @Date 2023/7/24
 */
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private final TacoOrderRepository tacoOrderRepository;
    private final OrderProps orderProps;

    public OrderController(TacoOrderRepository tacoOrderRepository, OrderProps orderProps) {
        this.tacoOrderRepository = tacoOrderRepository;
        this.orderProps = orderProps;
    }

    //asd
    @GetMapping("/current")
    public String orderForm(TacoOrder tacoOrder) {
        if (tacoOrder.getTacos().isEmpty()) {
            return "redirect:/design";
        }
        return "orderForm";
    }


    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        tacoOrderRepository.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        PageRequest page = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", tacoOrderRepository.findByUserOrderByPlacedAtDesc(user, page));
        return "orderList";
    }
}
