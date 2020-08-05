package com.mirocidij.simpleconsoleapplication.repositories.developer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mirocidij.simpleconsoleapplication.models.Skill;
import com.mirocidij.simpleconsoleapplication.utils.GsonUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SkillAdapter extends TypeAdapter<Set<Skill>> {
    @Override
    public void write(JsonWriter out, Set<Skill> skills) throws IOException {
        if (skills == null) {
            out.nullValue();
            return;
        }

        out.beginArray();

        for (Skill skill : skills) {
            out.beginObject();

            out.name("id").value(skill.getId());

            out.endObject();
        }

        out.endArray();
    }

    @Override
    public Set<Skill> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        if (in.peek() == JsonToken.BEGIN_ARRAY) {
            in.beginArray();
            var skills = new HashSet<Skill>();

            while (in.hasNext()) {
                var skill = new Skill();

                in.beginObject();

                GsonUtil.mapId(skill, in);

                in.endObject();

                skills.add(skill);
            }

            in.endArray();

            return skills;
        }

        return null;
    }
}
