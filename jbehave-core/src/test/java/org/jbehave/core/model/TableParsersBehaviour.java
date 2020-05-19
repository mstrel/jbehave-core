package org.jbehave.core.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.jbehave.core.model.ExamplesTable.ExamplesTableProperties;
import org.jbehave.core.model.ExamplesTable.ParsedExamplesTableData;

public class TableParsersBehaviour {

    private final TableParsers tableParsers = new TableParsers();

    @Test
    public void shouldParseTableUsingProperties() {
        // Given
        String table = "!key-1  !key-2  !\n"
                     + "|val-1-1|val-1-2|\n"
                     + "|val-2-1|val-2-2|\n";

        Properties properties = new Properties();
        properties.put("headerSeparator", "!");
        properties.put("valueSeparator", "|");
        ExamplesTableProperties tableProperties = new ExamplesTableProperties(properties);

        // When
        ParsedExamplesTableData parsedExamplesTable = tableParsers.parseByRows(table, tableProperties);

        // Then
        assertThat(parsedExamplesTable.getHeaders(), equalTo(Arrays.asList("key-1", "key-2")));
        List<Map<String, String>> data = parsedExamplesTable.getData();
        assertThat(data, hasSize(2));
        Map<String, String> first = new HashMap<>();
        first.put("key-1", "val-1-1");
        first.put("key-2", "val-1-2");
        assertThat(data.get(0), equalTo(first));
        Map<String, String> second = new HashMap<>();
        second.put("key-1", "val-2-1");
        second.put("key-2", "val-2-2");
        assertThat(data.get(1), equalTo(second));
    }
}
