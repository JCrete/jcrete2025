package com.pivovarit.json;

import java.util.Iterator;
import java.util.Map;

public class JsonPrettyPrinter {

    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String RED = "\u001B[31m";

    private static final String INDENT = "  ";

    public static String prettyPrint(JsonNode node) {
        return prettyPrint(node, 0);
    }

    private static String prettyPrint(JsonNode node, int indentLevel) {
        return switch (node) {
            case JsonNode.JsonObject obj -> printObject(obj, indentLevel);
            case JsonNode.JsonArray arr -> printArray(arr, indentLevel);
            case JsonNode.JsonString str -> GREEN + "\"" + escape(str.value()) + "\"" + RESET;
            case JsonNode.JsonNumber num -> CYAN + num.value() + RESET;
            case JsonNode.JsonBoolean bool -> MAGENTA + bool.value() + RESET;
            case JsonNode.JsonNull __ -> RED + "null" + RESET;
        };
    }

    private static String printObject(JsonNode.JsonObject obj, int indentLevel) {
        if (obj.entries().isEmpty()) return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        Iterator<Map.Entry<String, JsonNode>> it = obj.entries().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> entry = it.next();
            indent(sb, indentLevel + 1);
            sb.append(YELLOW).append("\"").append(escape(entry.getKey())).append("\"").append(RESET);
            sb.append(": ");
            sb.append(prettyPrint(entry.getValue(), indentLevel + 1));
            if (it.hasNext()) sb.append(",");
            sb.append("\n");
        }
        indent(sb, indentLevel);
        sb.append("}");
        return sb.toString();
    }

    private static String printArray(JsonNode.JsonArray arr, int indentLevel) {
        if (arr.elements().isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        Iterator<JsonNode> it = arr.elements().iterator();
        while (it.hasNext()) {
            JsonNode elem = it.next();
            indent(sb, indentLevel + 1);
            sb.append(prettyPrint(elem, indentLevel + 1));
            if (it.hasNext()) sb.append(",");
            sb.append("\n");
        }
        indent(sb, indentLevel);
        sb.append("]");
        return sb.toString();
    }

    private static void indent(StringBuilder sb, int level) {
        sb.append(INDENT.repeat(level));
    }

    private static String escape(String s) {
        return s
          .replace("\\", "\\\\")
          .replace("\"", "\\\"")
          .replace("\n", "\\n")
          .replace("\r", "\\r")
          .replace("\t", "\\t");
    }
}
