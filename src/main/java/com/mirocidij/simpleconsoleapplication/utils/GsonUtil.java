package com.mirocidij.simpleconsoleapplication.utils;

import com.google.gson.stream.JsonReader;
import com.mirocidij.simpleconsoleapplication.generic.entity.Entity;

import java.io.IOException;

public class GsonUtil {
    public static Entity<Long> mapId(Entity<Long> destination, JsonReader in) throws IOException {
        if (in.hasNext()) {
            String id = in.nextName();
            if (!id.equals("id")) {
                in.nextNull();
                return null;
            }

            var lId = ParseUtils.tryParseLong(in.nextString());
            if (lId == null) {
                in.nextNull();
                return null;
            }

            destination.setId(lId);
        }

        return destination;
    }
}
