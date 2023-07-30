package org.sxs.tacocloud.repository;

import org.sxs.tacocloud.domain.TacoOrder;

/**
 * @Author sxs
 * @Date 2023/7/30 13:20
 */
public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
