package org.mule.modules.cassandradb.internal.util;

import java.util.HashMap;
import java.util.Map;

public class ConnectionUtil {
    private ConnectionUtil() {
        // empty constructor
    }

    private static final String CASSANDRA_NODE_DEFAULT_PORT = "9042";

    public static Map<String,String> parseClusterNodesString(String clusterNodesString){
        Map<String,String> addresses = new HashMap<>();

        String[] parts = clusterNodesString.split(",");

        for(String part : parts) {
            if(part.contains(":")){
                String pair[] = part.split(":");

                addresses.put(pair[0].trim(), pair[1].trim());
            }

            else {
                addresses.put(part.trim(),CASSANDRA_NODE_DEFAULT_PORT);
            }
        }

        return addresses;
    }
}