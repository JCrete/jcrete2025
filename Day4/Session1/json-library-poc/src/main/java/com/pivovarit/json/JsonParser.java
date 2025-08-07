package com.pivovarit.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonParser {
    private final String input;
    private int pos = 0;

    public JsonParser(String input) {
        Objects.requireNonNull(input, "input must not be null");
        this.input = input;
    }

    public JsonNode parse() {
        skipWhitespace();
        return parseValue();
    }

    private JsonNode parseValue() {
        skipWhitespace();
        return switch (peek()) {
            case '{' -> parseObject();
            case '[' -> parseArray();
            case '"' -> parseString();
            case 't', 'f' -> parseBoolean();
            case 'n' -> parseNull();
            default -> parseNumber();
        };
    }

    private JsonNode.JsonObject parseObject() {
        expect('{');
        Map<String, JsonNode> map = new LinkedHashMap<>();
        skipWhitespace();
        if (peek() == '}') {
            expect('}');
            return new JsonNode.JsonObject(map);
        }

        while (true) {
            skipWhitespace();
            String key = parseString().value();
            skipWhitespace();
            expect(':');
            skipWhitespace();
            JsonNode value = parseValue();
            map.put(key, value);
            skipWhitespace();
            if (peek() == '}') {
                expect('}');
                break;
            }
            expect(',');
        }

        return new JsonNode.JsonObject(map);
    }

    private JsonNode.JsonArray parseArray() {
        expect('[');
        List<JsonNode> list = new ArrayList<>();
        skipWhitespace();
        if (peek() == ']') {
            expect(']');
            return new JsonNode.JsonArray(list);
        }

        while (true) {
            skipWhitespace();
            list.add(parseValue());
            skipWhitespace();
            if (peek() == ']') {
                expect(']');
                break;
            }
            expect(',');
        }

        return new JsonNode.JsonArray(list);
    }

    private JsonNode.JsonString parseString() {
        expect('"');
        StringBuilder sb = new StringBuilder();
        while (peek() != '"') {
            if (peek() == '\\') {
                advance();
                char escaped = advance();
                switch (escaped) {
                    case '"' -> sb.append('"');
                    case '\\' -> sb.append('\\');
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    default -> sb.append(escaped);
                }
            } else {
                sb.append(advance());
            }
        }
        expect('"');
        return new JsonNode.JsonString(sb.toString());
    }

    private JsonNode.JsonNumber parseNumber() {
        int start = pos;
        if (peek() == '-') {
            advance();
        }
        while (Character.isDigit(peek())) {
            advance();
        }
        if (peek() == '.') {
            do {
                advance();
            } while (Character.isDigit(peek()));
        }
        BigDecimal value = new BigDecimal(input.substring(start, pos));
        return new JsonNode.JsonNumber(value);
    }

    private JsonNode.JsonBoolean parseBoolean() {
        if (input.startsWith("true", pos)) {
            pos += 4;
            return new JsonNode.JsonBoolean(true);
        } else if (input.startsWith("false", pos)) {
            pos += 5;
            return new JsonNode.JsonBoolean(false);
        }
        throw new RuntimeException("Expected boolean at position " + pos);
    }

    private JsonNode.JsonNull parseNull() {
        if (input.startsWith("null", pos)) {
            pos += 4;
            return new JsonNode.JsonNull();
        }
        throw new RuntimeException("Expected null at position " + pos);
    }

    private void expect(char c) {
        if (advance() != c) {
            throw new RuntimeException("Expected '" + c + "' at position " + pos);
        }
    }

    private char peek() {
        skipWhitespace();
        if (pos >= input.length()) {
            throw new RuntimeException("Unexpected end of input");
        }
        return input.charAt(pos);
    }

    private char advance() {
        return input.charAt(pos++);
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }
}
