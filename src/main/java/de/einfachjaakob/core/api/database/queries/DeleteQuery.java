package de.einfachjaakob.core.api.database.queries;


import de.einfachjaakob.core.api.database.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class DeleteQuery {

    private String table;
    private List<String> wheres = new ArrayList<String>();

    public DeleteQuery(String table) {
        this.table = table;
    }


    public DeleteQuery where(String expression) {
        wheres.add(expression);
        return this;
    }


    public DeleteQuery and(String expression) {
        where(expression);
        return this;
    }


    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ")
                .append(table);

        if (wheres.size() > 0) {
            builder.append(" WHERE ")
                    .append(QueryUtils.separate(wheres, " AND "));
        }

        return builder.toString();
    }

}
