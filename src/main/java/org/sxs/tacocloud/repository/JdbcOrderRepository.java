package org.sxs.tacocloud.repository;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.sxs.tacocloud.domain.IngredientRef;
import org.sxs.tacocloud.domain.Taco;
import org.sxs.tacocloud.domain.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author sxs
 * @Date 2023/7/30 14:52
 */
@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations operations) {
        this.jdbcOperations = operations;
    }

    @Override
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco_Order (delivery_Name,delivery_Street,delivery_City,delivery_State,delivery_Zip,cc_number,cc_expiration,cc_cvv,placed_at) values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(), order.getDeliveryStreet(), order.getDeliveryCity(),
                        order.getDeliveryState(), order.getDeliveryZip(), order.getCcNumber(),
                        order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        int i = 0;
        for (Taco taco : order.getTacos()) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    private void saveTaco(long orderId, int orderKey, Taco taco) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco " + "(name, created_at, taco_order, taco_order_key) " + "values (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
        pscf.setReturnGeneratedKeys(true);
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                taco.getName(), taco.getCreatedAt(), orderId,
                orderKey));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        saveIngredientRefs(tacoId, taco.getIngredients());
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredients) {
        int key = 0;
        for (IngredientRef ingredient : ingredients) {
            jdbcOperations.update("insert into Ingredient_Ref (ingredient, taco, taco_key) values (?,?,?)",
                    ingredient.getIngredient(), tacoId, key++);
        }
    }
}
