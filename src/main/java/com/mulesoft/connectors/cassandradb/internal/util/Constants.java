/**
 * (c) 2003-2017 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.cassandradb.internal.util;

public class Constants {
	
	private Constants() {
	}

    public final static String REPLICATION_FACTOR = "replication_factor";
    public final static String CLASS = "class";
    public static final String COLUMNS = "columns";
    public static final String WHERE = "where";

    //query specific constants
    public static final String PARAM_HOLDER = "?";
    public static final String SELECT = "SELECT";

    public static final int DEFAULT_REPLICATION_FACTOR = 3;
}
