package com.pusher.rest.data;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;

public class TriggerResult extends Result {
    static class Data {
        public final Map<String, String> eventIds;

        public Data(final Map<String, String> eventIds) {
            this.eventIds = eventIds;
        }
    }

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    protected TriggerResult(Status status, Integer httpStatus, String message) {
        super(status, httpStatus, message);
    }

    public static TriggerResult fromResult(final Result result) {
        return new TriggerResult(
            result.getStatus(),
            result.getHttpStatus(),
            result.getMessage()
        );
    }

    /**
     * Get the map from channel name to event ID.
     *
     * If the response statud != SUCCESS or when talking to old APIS, the
     * return value will be null.
     */
    public Map<String, String> getEventIDs() {
        if (this.status != Status.SUCCESS) {
            return null;
        }

        final Data obj = GSON.fromJson(this.message, Data.class);

        return obj.eventIds;
    }
}