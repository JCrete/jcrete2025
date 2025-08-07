package com.pivovarit.json;

import java.util.ArrayList;
import java.util.List;

public class JsonPath {

    static String[] tokenize(String path) {
        path = path.substring(1); // remove $
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        boolean inBracket = false;
        boolean inQuotes = false;

        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);

            if (inBracket) {
                if (c == '\'' || c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ']' && !inQuotes) {
                    inBracket = false;
                    tokens.add(token.toString());
                    token.setLength(0);
                } else {
                    token.append(c);
                }
            } else {
                if (c == '.') {
                    if (!token.isEmpty()) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                } else if (c == '[') {
                    if (!token.isEmpty()) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                    inBracket = true;
                } else {
                    token.append(c);
                }
            }
        }

        if (!token.isEmpty()) {
            tokens.add(token.toString());
        }

        return tokens.toArray(String[]::new);
    }
}
