package org.sxs.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author sxs
 * @date 9/8/2023 17:11
 */
@Component
public class TacoCountInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "sxs");
        builder.withDetails(map);
    }
}
