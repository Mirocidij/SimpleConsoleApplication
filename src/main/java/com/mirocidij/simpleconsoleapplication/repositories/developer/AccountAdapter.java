package com.mirocidij.simpleconsoleapplication.repositories.developer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.mirocidij.simpleconsoleapplication.models.Account;
import com.mirocidij.simpleconsoleapplication.utils.GsonUtil;

import java.io.IOException;

public class AccountAdapter extends TypeAdapter<Account> {
    @Override
    public void write(JsonWriter out, Account account) throws IOException {
        if (account == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("id");
        out.value(account.getId());
        out.endObject();
    }

    @Override
    public Account read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        if (in.peek() == JsonToken.BEGIN_OBJECT) {
            in.beginObject();

            var account = new Account();

            GsonUtil.mapId(account, in);

            in.endObject();

            return account;
        }

        return null;
    }
}
