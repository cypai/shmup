package com.pipai.shmup;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Integer> fetchEntities(World world, Aspect.Builder aspects) {
        IntBag entityBag = world.getAspectSubscriptionManager()
                .get(aspects)
                .getEntities();
        List<Integer> entities = new ArrayList<>();
        for (int i = 0; i < entityBag.size(); i++) {
            entities.add(entityBag.get(i));
        }
        return entities;
    }

}
