package de.einfachjaakob.core.api.database.queries;


import de.einfachjaakob.core.api.database.utils.QueryUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class InsertQuery {

    private String table;
    private LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> duplicateValues = new LinkedHashMap<String, String>();
    private boolean onDuplicateKey = false;
    private boolean ignore = false;


    public InsertQuery(String table) {
        this.table = table;
    }


    public InsertQuery value(String column, String value) {
        values.put(column, value);
        return this;
    }

    public InsertQuery value(String column) {
        value(column, "?");
        return this;
    }

    public InsertQuery onDuplicateKeyUpdate() {
        onDuplicateKey = true;
        return this;
    }

    public InsertQuery set(String column, String value) {
        duplicateValues.put(column, value);
        return this;
    }

    public InsertQuery ignore() {
        ignore = true;
        return this;
    }

    public InsertQuery set(String column) {
        set(column, "VALUES(" + column + ")");
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT ");

        if (ignore) {
            builder.append("IGNORE ");
        }

        builder.append("INTO ")
                .append(table)
                .append(" (")
                .append(QueryUtils.separate(values.keySet(), ","))
                .append(")")
                .append(" VALUES (")
                .append(QueryUtils.separate(values.values(), ","))
                .append(")");

        if (onDuplicateKey) {
            builder.append(" ON DUPLICATE KEY UPDATE ");
            String separator = "";
            for (Map.Entry<String, String> entry : duplicateValues.entrySet()) {
                String column = entry.getKey();
                String value = entry.getValue();
                builder.append(separator)
                        .append(column)
                        .append("=")
                        .append(value);
                separator = ",";
            }
        }

        return builder.toString();
    }

}
