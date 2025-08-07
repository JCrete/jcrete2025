package com.pivovarit.json;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public sealed interface JsonNode {
    String type();

    default <T> T map(Mapper<T> mapper) {
        return switch (this) {
            case JsonObject obj -> mapper.map(obj);
            default -> throw new IllegalArgumentException("unexpected node type: " + this.type());
        };
    }

    default JsonNode resolve(String path) {
        if (!path.startsWith("$")) {
            throw new IllegalArgumentException("Path must start with '$'");
        }

        String[] tokens = JsonPath.tokenize(path);
        JsonNode current = this;

        for (String token : tokens) {
            if (current instanceof JsonNode.JsonObject(java.util.Map<String, JsonNode> entries)) {
                current = entries.get(token);
                if (current == null) {
                    return null;
                }
            } else if (current instanceof JsonNode.JsonArray(List<JsonNode> elements)) {
                try {
                    int index = Integer.parseInt(token);
                    if (index < 0 || index >= elements.size()) {
                        return null;
                    }
                    current = elements.get(index);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid array index: " + token);
                }
            } else {
                return null;
            }
        }

        return current;
    }

    default String getString() {
        return getString(this);
    }

    default String getString(String path) {
        return getString(this.resolve(path));
    }

    private String getString(JsonNode node) {
        return switch (node) {
            case JsonArray ignored -> throw new IllegalArgumentException("unexpected node type: " + this.type());
            case JsonBoolean(boolean value) -> Boolean.toString(value);
            case JsonNull ignored -> "null";
            case JsonNumber(BigDecimal value) -> value.toString();
            case JsonObject ignored -> throw new IllegalArgumentException("unexpected node type: " + this.type());
            case JsonString(String value) -> value;
        };
    }

    @FunctionalInterface
    interface Mapper<T> {
        T map(JsonObject node);
    }

    record JsonObject(Map<String, JsonNode> entries) implements JsonNode {
        @Override
        public String type() {
            return "object";
        }
    }

    record JsonArray(Collection<JsonNode> elements) implements JsonNode {
        @Override
        public String type() {
            return "array";
        }
    }

    record JsonString(String value) implements JsonNode {
        @Override
        public String type() {
            return "string";
        }
    }

    record JsonNumber(BigDecimal value) implements JsonNode {

        @Override
        public String type() {
            return "number";
        }
    }

    record JsonBoolean(boolean value) implements JsonNode {

        @Override
        public String type() {
            return "boolean";
        }
    }

    record JsonNull() implements JsonNode {
        @Override
        public String type() {
            return "null";
        }
    }
}

