package com.pivovarit.example;

import com.pivovarit.json.JsonNode;
import com.pivovarit.json.JsonParser;
import com.pivovarit.json.JsonPrettyPrinter;

public class Main {
    public static void main(String[] args) {
        String json = """
          {
            "name": "Alice",
            "age": 30,
            "married": false,
            "children": ["Bob", "Charlie"],
            "address": { "city": "Wonderland", "zip": "12345" },
            "hobbies": null
          }
          """;

        JsonParser parser = new JsonParser(json);
        JsonNode root = parser.parse();

        System.out.println("parsed:");
        System.out.println(JsonPrettyPrinter.prettyPrint(root));

        System.out.println("root.resolve(\"$.name\") = " + root.resolve("$.name"));
        System.out.println("root.resolve(\"$.age\") = " + root.resolve("$.age"));

        System.out.println("root.getString(\"$.married\") = " + root.getString("$.married"));

        Person person = root.map(obj -> new Person(
          obj.getString("$.name"),
          Integer.parseInt(obj.getString("$.age"))));

        System.out.println("person = " + person);
    }

    record Person(String name, int age) {
    }
}
