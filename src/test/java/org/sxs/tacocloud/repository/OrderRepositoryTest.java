package org.sxs.tacocloud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.sxs.tacocloud.domain.Ingredient;
import org.sxs.tacocloud.domain.Ingredient.Type;
import org.sxs.tacocloud.domain.Taco;
import org.sxs.tacocloud.domain.TacoOrder;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author sxs
 * @Date 2023/8/2 20:28
 */
@DataJpaTest(showSql = true)
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void saveOrderWithTwoTacos() {
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientRepository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
        System.out.println(ingredientRepository.count());
        TacoOrder order = new TacoOrder();
        order.setDeliveryName("Test McTest");
        order.setDeliveryStreet("1234 Test Lane");
        order.setDeliveryCity("Testville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("80123");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/23");
        order.setCcCVV("123");
        order.setCcCVV("123");
        Taco taco1 = new Taco();
        taco1.setName("Taco One");
        taco1.addIngredient(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        taco1.addIngredient(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        taco1.addIngredient(new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE));
        order.addTaco(taco1);
        Taco taco2 = new Taco();
        taco2.setName("Taco Two");
        taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        taco2.addIngredient(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        order.addTaco(taco2);
        TacoOrder savedOrder = orderRepository.save(order);
        assertThat(order.getId()).isNotNull();

        TacoOrder fetchedOrder = orderRepository.findById(order.getId()).get();
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
        assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
        System.out.println(fetchedOrder.toString());
        List<Taco> tacos = fetchedOrder.getTacos();
        assertThat(tacos.size()).isEqualTo(2);
        assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
        System.out.println("save 完成");
    }

    @Test
    void findByDeliveryZip() {
        System.out.println("findByDeliveryZip....");
        List<TacoOrder> byDeliveryZip = orderRepository.findByDeliveryZip("80123");
        assertThat(byDeliveryZip.size()).isEqualTo(1);
        List<TacoOrder> byDeliveryZip1 = orderRepository.findByDeliveryZip("xxxx");
        assertThat(byDeliveryZip1.size()).isEqualTo(0);
    }

    @Test
    void readOrdersByDeliveryZipAndPlacedAtBetween() {
        List<TacoOrder> tacoOrders = orderRepository.readOrdersByDeliveryZipAndPlacedAtBetween("80123", new Date(new Date().getTime() - 1000000), new Date());
        assertThat(tacoOrders.size()).isEqualTo(1);
        tacoOrders = orderRepository.readOrdersByDeliveryZipAndPlacedAtBetween("80123", new Date(new Date().getTime()), new Date());
        assertThat(tacoOrders.size()).isEqualTo(0);
    }

    @Test
    void findByDeliveryNameAndDeliveryCityAllIgnoreCase() {
        List<TacoOrder> orderList = orderRepository.findByDeliveryNameAndDeliveryCityAllIgnoreCase("Test McTest", "Testville");
        assertThat(orderList.size()).isEqualTo(1);
        orderList = orderRepository.findByDeliveryNameAndDeliveryCityAllIgnoreCase("test mcTest", "testville");
        assertThat(orderList.size()).isEqualTo(1);
    }

    @Test
    void findByDeliveryCityOrderByDeliveryName() {

    }

    @Test
    void readOrdersDeliveredInSeattle() {
        List<TacoOrder> tacoOrders = orderRepository.readOrdersDeliveredInSeattle();
        assertThat(tacoOrders.size()).isEqualTo(0);
    }
}