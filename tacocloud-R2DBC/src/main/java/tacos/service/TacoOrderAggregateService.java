package tacos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tacos.domain.Taco;
import tacos.domain.TacoOrder;
import tacos.repository.OrderRepository;
import tacos.repository.TacoRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * @author sxs
 * @date 9/1/2023 14:04
 */
@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {
    private final TacoRepository tacoRepo;
    private final OrderRepository orderRepo;

    public Mono<TacoOrder> save(TacoOrder tacoOrder) {
        return Mono.just(tacoOrder)
                .flatMap(order -> {
                    List<Taco> tacos = order.getTacos();
                    order.setTacos(new ArrayList<>());
                    return tacoRepo.saveAll(tacos)
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            }).last();
                })
                .flatMap(order -> {
                    return orderRepo.save(order);
                });
    }

    public Mono<TacoOrder> findById(Long id) {
        return orderRepo
                .findById(id)
                .flatMap(order -> {
                    return tacoRepo.findAllById(order.getTacoIds())
                            .map(taco -> {
                                order.addTaco(taco);
                                return order;
                            }).last();
                });
    }
}
