package com.app;

import com.app.model.Worker;
import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App 
{
    static String createWorkerTableSql(Worker worker) {

        Class<Worker> workerClass = Worker.class;

        // NAZWA TABELI
        var pluralTableName = English.plural(workerClass.getSimpleName());
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pluralTableName);

        // NAZWY POSZCZEGOLNYCH KOLUMN + WARTOSCI KOLUMN
        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();

        Arrays
                .stream(workerClass.getDeclaredFields())
                .forEach(field -> {
                    try {
                        // ustalenie nazwy kolumny
                        columnNames.add(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));

                        // ustalenie wartosci dla kolumny
                        field.setAccessible(true);
                        if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {
                            columnValues.add("'" + field.get(worker) + "'");
                        } else {
                            columnValues.add(field.get(worker).toString());
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException(e.getMessage());
                    }
                });

        return
                "insert into " + tableName +
                " (" + columnNames.stream().collect(Collectors.joining(", ")) + ") values (" +
                columnValues.stream().collect(Collectors.joining(", ")) + ");";
    }

    public static void main( String[] args )
    {
        final String camelCaseValue = "ThisIsCamelCase";
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCaseValue));
        System.out.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelCaseValue));

        final String snakeCaseValue = "this_is_snake_case";
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, snakeCaseValue));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, snakeCaseValue));

        final String english1 = "boy";
        System.out.println(English.plural(english1));
        final String english2 = "copy";
        System.out.println(English.plural(english2));
        final String english3 = "loyalty card";
        System.out.println(English.plural(english3));
        final String english4 = "baculum";
        System.out.println(English.plural(english4));
        final String english5 = "bus";
        System.out.println(English.plural(english5));
        final String english6 = "wife";
        System.out.println(English.plural(english6));
        final String english7 = "potato";
        System.out.println(English.plural(english7));
        final String english8 = "analysis";
        System.out.println(English.plural(english8));
        final String english9 = "sheep";
        System.out.println(English.plural(english9));

        var worker = Worker.builder().id(1).name("JOHN").birthDate(LocalDate.now()).favouriteColor("RED").build();
        System.out.println(createWorkerTableSql(worker));
    }
}
