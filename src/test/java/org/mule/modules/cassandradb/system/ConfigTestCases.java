/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cassandradb.system;

import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;
import org.junit.Test;
import org.mule.modules.cassandradb.internal.exception.CassandraException;
import org.mule.modules.cassandradb.internal.connection.AdvancedConnectionParameters;
import org.mule.modules.cassandradb.internal.connection.CassandraConnection;
import org.mule.modules.cassandradb.internal.connection.ConnectionParameters;
import org.mule.modules.cassandradb.util.CassandraProperties;
import org.mule.modules.cassandradb.util.PropertiesLoaderUtil;
import org.mule.modules.cassandradb.util.TestsConstants;

import java.io.IOException;


public class ConfigTestCases {

    private static String INVALID_HOST = "INVALID_HOST";

    private static CassandraProperties cassProperties;

    public static CassandraProperties getCassandraProperties() {
        //load required properties
        CassandraProperties cassProperties = null;
        try {
            cassProperties = PropertiesLoaderUtil.resolveCassandraConnectionProps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert cassProperties != null;
        return cassProperties;
    }

    @Test
    public void shouldConnect_Using_BasicParams() throws Exception {
        //given
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection cassClient = CassandraConnection.buildCassandraClient(new ConnectionParameters(cassProperties.getHost(), cassProperties.getPort(), null, null, null, null));
        //then
        assert cassClient != null;
    }

    @Test
    public void shouldConnect_Using_AdvancedParams() throws Exception {
        //given
        AdvancedConnectionParameters advancedParams = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection cassClient = CassandraConnection.buildCassandraClient(new ConnectionParameters(cassProperties.getHost(), cassProperties.getPort(), null, null, null, advancedParams));
        //then
        assert cassClient != null;
    }

    @Test(expected = CassandraException.class)
    public void shouldNotConnect_Using_InvalidHost() {
        //given
        AdvancedConnectionParameters advancedParams = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection.buildCassandraClient(new ConnectionParameters(INVALID_HOST, cassProperties.getPort(), null, null, null, advancedParams));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConnect_Using_NoHost() {
        //given
        AdvancedConnectionParameters advancedParams = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection.buildCassandraClient(new ConnectionParameters(null, cassProperties.getPort(), null, null, null, advancedParams));
    }

    @Test(expected = CassandraException.class)
    public void shouldNotConnect_Using_InvalidPort() {
        //given
        AdvancedConnectionParameters advancedParams = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection.buildCassandraClient(new ConnectionParameters(cassProperties.getHost(), generateInvalidPort(cassProperties.getPort()), null, null, null, advancedParams));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConnect_Using_NoPort() {
        //given
        AdvancedConnectionParameters advancedParams = new AdvancedConnectionParameters(ProtocolVersion.V3, TestsConstants.CLUSTER_NAME, TestsConstants.MAX_WAIT, ProtocolOptions.Compression.NONE, false);
        cassProperties = getCassandraProperties();
        //when
        CassandraConnection.buildCassandraClient(new ConnectionParameters(cassProperties.getHost(), null, null, null, null, advancedParams));
    }

    private String generateInvalidPort(String port) {
        Integer intPort = Integer.parseInt(port) - 1;
        return String.valueOf(intPort);
    }
}
