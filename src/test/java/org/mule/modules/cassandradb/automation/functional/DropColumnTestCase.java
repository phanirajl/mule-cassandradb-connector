/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.automation.functional;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.TableMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cassandradb.api.CreateTableInput;
import org.mule.modules.cassandradb.automation.util.TestsConstants;
import org.mule.modules.cassandradb.internal.exception.CassandraError;
import org.mule.tck.junit4.matcher.ErrorTypeMatcher;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.getBasicCreateTableInput;
import static org.mule.modules.cassandradb.automation.functional.TestDataBuilder.getColumns;
import static org.mule.modules.cassandradb.automation.util.TestsConstants.COLUMN;
import static org.mule.modules.cassandradb.automation.util.TestsConstants.TABLE_NAME_1;
import static org.mule.modules.cassandradb.automation.util.TestsConstants.VALID_COLUMN_1;
import static org.mule.modules.cassandradb.automation.util.TestsConstants.VALID_COLUMN_2;
import static org.mule.modules.cassandradb.internal.exception.CassandraError.QUERY_VALIDATION;


public class DropColumnTestCase extends AbstractTestCases {

    @Before
    public void setup() throws Exception {
        CreateTableInput basicCreateTableInput = getBasicCreateTableInput(getColumns(), getKeyspaceFromProperties(), TABLE_NAME_1);
        getCassandraService().createTable(basicCreateTableInput);
    }

    @After
    public void tearDown() {
        getCassandraService().dropTable(TABLE_NAME_1, getKeyspaceFromProperties());
    }

    @Test
    public void testRemoveColumnWithSuccess() throws Exception {
        assertTrue(dropColumn(TABLE_NAME_1, getKeyspaceFromProperties(), VALID_COLUMN_1));
        assertTrue(dropColumn(TABLE_NAME_1, getKeyspaceFromProperties(), VALID_COLUMN_2));

        Thread.sleep(SLEEP_DURATION);
        TableMetadata tableMetadata = fetchTableMetadata(getKeyspaceFromProperties(), TABLE_NAME_1);
        ColumnMetadata column = tableMetadata.getColumn(VALID_COLUMN_1);
        assertNull(column);
        ColumnMetadata column2 = tableMetadata.getColumn(VALID_COLUMN_2);
        assertNull(column2);
    }

    @Test
    public void testRemoveColumnWithInvalidName() throws Exception {
        dropColumnExpException(TABLE_NAME_1, getKeyspaceFromProperties(), COLUMN, QUERY_VALIDATION );
    }

    boolean dropColumn(String tableName, String keyspaceName, String column) throws Exception {
        return (boolean) flowRunner("dropColumn-flow")
                .withPayload(column)
                .withVariable("tableName", tableName)
                .withVariable("keyspaceName", keyspaceName)
                .run()
                .getMessage()
                .getPayload()
                .getValue();
    }

    void dropColumnExpException(String tableName, String keyspaceName, String column, CassandraError error) throws Exception {
        flowRunner("dropColumn-flow")
                .withPayload(column)
                .withVariable("tableName", tableName)
                .withVariable("keyspaceName", keyspaceName)
                .runExpectingException(ErrorTypeMatcher.errorType(error));
    }
}
