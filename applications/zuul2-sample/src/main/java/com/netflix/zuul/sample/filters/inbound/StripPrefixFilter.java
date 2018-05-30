package com.netflix.zuul.sample.filters.inbound;

import com.netflix.zuul.context.SessionContext;
import com.netflix.zuul.filters.http.HttpInboundSyncFilter;
import com.netflix.zuul.message.http.HttpRequestMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StripPrefixFilter extends HttpInboundSyncFilter {
    private final List<String> prefixPatterns;

    public StripPrefixFilter(List<String> prefixPatterns) {
        this.prefixPatterns = prefixPatterns;
    }

    @Override
    public HttpRequestMessage apply(HttpRequestMessage input) {
        SessionContext context = input.getContext();
        String path = input.getPath();
        String[] parts = path.split("/");
        if (parts.length > 0) {
            String targetPath = Arrays.stream(parts)
                    .skip(1).collect(Collectors.joining("/"));
            context.set("overrideURI", targetPath);
        }
        return input;
    }

    @Override
    public int filterOrder() {
        return 501;
    }

    @Override
    public boolean shouldFilter(HttpRequestMessage msg) {
        for (String target: prefixPatterns) {
            if (msg.getPath().matches(target)) {
                return true;
            }
        }
        return false;
    }
}
