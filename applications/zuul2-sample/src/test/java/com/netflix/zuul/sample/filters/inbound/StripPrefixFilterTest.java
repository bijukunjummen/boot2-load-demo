package com.netflix.zuul.sample.filters.inbound;

import com.netflix.zuul.message.http.HttpRequestInfo;
import com.netflix.zuul.message.http.HttpRequestMessage;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StripPrefixFilterTest {
    
    @Test
    public void testShouldFilter() {
        StripPrefixFilter filter = new StripPrefixFilter(Arrays.asList("/path1/.*"));
        assertThat(filter.shouldFilter(sampleHttpMessage("/path1/testRemaining?query1=val1"))).isTrue();
        assertThat(filter.shouldFilter(sampleHttpMessage("/path2/testRemaining?query1=val1"))).isFalse();
    }

    private HttpRequestMessage sampleHttpMessage(String path) {
        HttpRequestMessage sampleRequest = mock(HttpRequestMessage.class);
        when(sampleRequest.getPath()).thenReturn(path);
        return sampleRequest;
    }
}
