/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.automation.functional;

import com.datastax.driver.core.DataType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cassandradb.api.CQLQueryInput;
import org.mule.modules.cassandradb.api.CreateTableInput;
import org.mule.modules.cassandradb.automation.util.TestDataBuilder;
import org.mule.modules.cassandradb.internal.exception.CassandraError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mule.modules.cassandradb.automation.util.TestDataBuilder.DUMMY_PARTITION_KEY;
import static org.mule.modules.cassandradb.automation.util.TestDataBuilder.TABLE_NAME_2;
import static org.mule.modules.cassandradb.automation.util.TestDataBuilder.VALID_COLUMN_2;
import static org.mule.modules.cassandradb.automation.util.TestDataBuilder.getPrimaryKey;
import static org.mule.modules.cassandradb.automation.util.TestDataBuilder.getValidEntity;
import static org.mule.modules.cassandradb.internal.exception.CassandraError.QUERY_VALIDATION;
import static org.mule.modules.cassandradb.internal.exception.CassandraError.UNKNOWN;
import static org.mule.tck.junit4.matcher.ErrorTypeMatcher.errorType;

public class ExecuteCqlQueryTestCase extends AbstractTestCases {

    private static String QUERY_PREFIX = "SELECT * FROM ";

    @Before
    public void setup() throws Exception {
        CreateTableInput basicCreateTableInput = TestDataBuilder.getBasicCreateTableInput(getPrimaryKey(), getKeyspaceFromProperties(), TABLE_NAME_2);
        getCassandraService().createTable(basicCreateTableInput);
        getCassandraService().addNewColumn(TABLE_NAME_2, getKeyspaceFromProperties(), VALID_COLUMN_2, DataType.ascii());
        getCassandraService().insert(getKeyspaceFromProperties(), TABLE_NAME_2, getValidEntity());
    }

    @After
    public void tearDown()  {
        getCassandraService().dropTable(TABLE_NAME_2, getKeyspaceFromProperties());
    }

    @Test
    public void shouldExecute_ValidParametrizedQuery() throws Exception {
        //Select * from dummy_table_name_2 WHERE dummy_partition_key = 'value1'
        List<Object> params = new ArrayList<Object>();
        params.add("value1");
        String whereClause = " WHERE " + DUMMY_PARTITION_KEY + " = ?";
        CQLQueryInput query = new CQLQueryInput(QUERY_PREFIX + TABLE_NAME_2 + whereClause, params);

        List<Map<String, Object>> queryResult = executeCQLQuery(query);
        assertNotNull(queryResult.get(0));
    }

    @Test
    public void shouldExecute_NonParametrizedQuery() throws Exception {
        CQLQueryInput query = new CQLQueryInput(QUERY_PREFIX + TABLE_NAME_2, new ArrayList<>());
        List<Map<String, Object>> queryResult = executeCQLQuery(query);
        assertNotNull(queryResult.get(0));
    }

    @Test
    public void shouldThrowException_When_InsufficientAmountOfBindVariables() throws Exception {
        //Select * from dummy_table_name_2 WHERE dummy_partition_key = ?
        String whereClause = " WHERE " + DUMMY_PARTITION_KEY + " = ?";
        CQLQueryInput query = new CQLQueryInput(QUERY_PREFIX + TABLE_NAME_2 + whereClause, null);

        executeCQLQueryExpException(query, QUERY_VALIDATION);
    }

    @Test
    public void shouldThrowException_When_InsufficientAmountOfParameters() throws Exception {
        //Select * from dummy_table_name_2
        List<Object> params = new ArrayList<Object>();
        params.add("value1");
        CQLQueryInput query = new CQLQueryInput(QUERY_PREFIX + TABLE_NAME_2, params);

        executeCQLQueryExpException(query, UNKNOWN);
    }

    protected List<Map<String, Object>> executeCQLQuery(CQLQueryInput query) throws Exception {
        return (List<Map<String, Object>>) flowRunner("executeCQLQuery-flow")
                .withPayload(query)
                .run()
                .getMessage()
                .getPayload()
                .getValue();
    }

    protected void executeCQLQueryExpException(CQLQueryInput query, CassandraError error) throws Exception {
        flowRunner("executeCQLQuery-flow")
                .withPayload(query)
                .runExpectingException(errorType(error));
    }
}
