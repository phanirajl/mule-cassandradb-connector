package com.mulesoft.connectors.cassandradb.internal.exception;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.extension.api.error.MuleErrors.ANY;

public enum CassandraError implements ErrorTypeDefinition<CassandraError> {
    CASSANDRA_EXECUTION(ANY),
    ALREADY_EXISTS(CASSANDRA_EXECUTION),
    AUTHENTICATION(CASSANDRA_EXECUTION),
    BOOTSTRAPPING(CASSANDRA_EXECUTION),
    BUSY_CONNECTION(CASSANDRA_EXECUTION),
    BUSY_POOL(CASSANDRA_EXECUTION),
    CODEC_NOT_FOUND(CASSANDRA_EXECUTION),
    CONNECTION(CASSANDRA_EXECUTION),
    DRIVER_INTERNAL_ERROR(CASSANDRA_EXECUTION),
    FRAME_TOO_LONG(CASSANDRA_EXECUTION),
    FUNCTION_EXECUTION(CASSANDRA_EXECUTION),
    INVALID_CONFIGURATION_IN_QUERY(CASSANDRA_EXECUTION),
    INVALID_QUERY(CASSANDRA_EXECUTION),
    INVALID_TYPE(CASSANDRA_EXECUTION),
    NO_HOST_AVAILABLE(CASSANDRA_EXECUTION),
    OPERATION_TIMED_OUT(CASSANDRA_EXECUTION),
    OVERLOADED(CASSANDRA_EXECUTION),
    PAGING_STATE(CASSANDRA_EXECUTION),
    PROTOCOL_ERROR(CASSANDRA_EXECUTION),
    QUERY_CONSISTENCY(CASSANDRA_EXECUTION),
    QUERY_EXECUTION(CASSANDRA_EXECUTION),
    QUERY_VALIDATION(CASSANDRA_EXECUTION),
    OPERATION_FAILED(CASSANDRA_EXECUTION),
    READ_FAILURE(CASSANDRA_EXECUTION),
    READ_TIMEOUT(CASSANDRA_EXECUTION),
    SERVERE_RROR(CASSANDRA_EXECUTION),
    SYNTAX_ERROR(CASSANDRA_EXECUTION),
    TRACE_RETRIEVAL(CASSANDRA_EXECUTION),
    TRANSPORT(CASSANDRA_EXECUTION),
    TRUNCATE(CASSANDRA_EXECUTION),
    UNAUTHORIZED(CASSANDRA_EXECUTION),
    UNAVAILABLE(CASSANDRA_EXECUTION),
    UNPREPARED(CASSANDRA_EXECUTION),
    UNRESOLVED_USER_TYPE(CASSANDRA_EXECUTION),
    UNSUPPORTED_FEATURE(CASSANDRA_EXECUTION),
    UNSUPPORTED_PROTOCOL_VERSION(CASSANDRA_EXECUTION),
    WRITE_FAILURE(CASSANDRA_EXECUTION),
    WRITE_TIMEOUT(CASSANDRA_EXECUTION),


    CASSANDRA_EXCEPTION(ANY),
    QUERY_ERROR(CASSANDRA_EXCEPTION),
    OPERATION_NOT_APPLIED(CASSANDRA_EXCEPTION),

    CONNECTIVITY(MuleErrors.CONNECTIVITY),
    UNKNOWN(CONNECTIVITY);

    private ErrorTypeDefinition<?> parentErrorType;

    CassandraError(final ErrorTypeDefinition parentErrorType) {
        this.parentErrorType = parentErrorType;
    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return ofNullable(parentErrorType);
    }
}
