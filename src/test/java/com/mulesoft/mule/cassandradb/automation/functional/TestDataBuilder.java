/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.mule.cassandradb.automation.functional;

import com.mulesoft.mule.cassandradb.util.ConstantsTest;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TestDataBuilder {

    protected static final String VALID_PARAMETERIZED_QUERY = "SELECT dummy_column FROM dummy_table WHERE dummy_partitionKey IN (?, ?)";
    protected static final String VALID_DSQL_QUERY = "dsql:SELECT dummy_column FROM dummy_table";

    private TestDataBuilder() {

    }

    @NotNull
    public static Map<String, Object> getInvalidEntity() {
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put("invalid_column", "someValue");
        return entity;
    }

    public static Map<String, Object> getValidEntity() {
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, "value1");
        entity.put(ConstantsTest.VALID_COLUMN, "someValue" + System.currentTimeMillis());
        return entity;
    }

    public static Map<String, Object> getValidEntityForUpdate() {
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put(ConstantsTest.VALID_COLUMN, "someValue" + System.currentTimeMillis());
        return entity;
    }

    public static Map<String, Object> getValidWhereClauseWithEq() {
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, "value1");
        return entity;
    }

    public static Map<String, Object> getValidWhereClauseWithIN() {
        Map<String, Object> entity = new HashMap<String, Object>();
        List list = new ArrayList();
        list.add("value1");
        list.add("value2");
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, list);
        return entity;
    }

    /* WHERE clause restrictions for the UPDATE are
    *   - the single-column EQ on any partition key or clustering columns
        - the single-column IN restriction on the last partition key column
    * */
    public static Map<String, Object> getInvalidWhereClause() {
        Map<String, Object> entity = new HashMap<String, Object>();
        entity.put(ConstantsTest.VALID_COLUMN, "someValue");
        return entity;
    }

    public static List<Object> getValidParmList() {
        List<Object> parameters = new LinkedList<>();
        parameters.add("value1");
        parameters.add("value2");
        return parameters;
    }

    public static List<String> getValidColumnsListForDelete() {
        List<String> parameters = new LinkedList<>();
        parameters.add(ConstantsTest.VALID_COLUMN);
        parameters.add(ConstantsTest.VALID_COLUMN_2);
        return parameters;
    }

    public static List<String> getInvalidEntityForDelete() {
        List<String> entity = new ArrayList<>();
        entity.add("invalid_column");
        return entity;
    }

    public static Map<String, Object> getValidEntityWithList() {
        Map<String, Object> entity = new HashMap<String, Object>();
        List<String> list = new ArrayList<>();
        list.add("firstValue");
        list.add("secondValue");
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, "value1");
        entity.put(ConstantsTest.VALID_LIST_COLUMN, list);
        entity.put(ConstantsTest.VALID_COLUMN, "someValue");
        return entity;
    }

    public static List<String> getValidListItem() {
        List<String> entity = new ArrayList<>();
        entity.add(ConstantsTest.VALID_LIST_COLUMN + "[0]");
        return entity;
    }

    public static Map<String, Object> getValidEntityWithMap() {
        Map<String, Object> entity = new HashMap<String, Object>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("1", "firstValue");
        item.put("2", "secondValue");
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, "value1");
        entity.put(ConstantsTest.VALID_MAP_COLUMN, item);
        entity.put(ConstantsTest.VALID_COLUMN, "someValue");
        return entity;
    }

    public static List<String> getValidMapItem() {
        List<String> entity = new ArrayList<>();
        entity.add(ConstantsTest.VALID_MAP_COLUMN + "['firstValue']");
        return entity;
    }

    public static Map<String, Object> getValidEntityWithSet() {
        Map<String, Object> entity = new HashMap<String, Object>();
        Set<String> item = new HashSet<>();
        item.add("firstValue");
        item.add("secondValue");
        entity.put(ConstantsTest.DUMMY_PARTITION_KEY, "value1");
        entity.put(ConstantsTest.VALID_SET_COLUMN, item);
        entity.put(ConstantsTest.VALID_COLUMN, "someValue");
        return entity;
    }

    public static List<String> getValidSet() {
        List<String> entity = new ArrayList<>();
        entity.add(ConstantsTest.VALID_SET_COLUMN);
        return entity;
    }
}
