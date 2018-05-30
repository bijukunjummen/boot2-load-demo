package com.netflix.zuul.sample;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.netflix.zuul.BasicFilterUsageNotifier;
import com.netflix.zuul.DynamicCodeCompiler;
import com.netflix.zuul.FilterFactory;
import com.netflix.zuul.FilterUsageNotifier;
import com.netflix.zuul.filters.ZuulFilter;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.guice.GuiceFilterFactory;
import com.netflix.zuul.sample.filters.inbound.Routes;
import com.netflix.zuul.sample.filters.inbound.StripPrefixFilter;

import java.util.Arrays;


public class ZuulClasspathFiltersModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DynamicCodeCompiler.class).to(GroovyCompiler.class);
        bind(FilterFactory.class).to(GuiceFilterFactory.class);

        bind(FilterUsageNotifier.class).to(BasicFilterUsageNotifier.class);

        Multibinder<ZuulFilter> filterMultibinder = Multibinder.newSetBinder(binder(), ZuulFilter.class);
        filterMultibinder.addBinding().to(Routes.class);
        filterMultibinder.addBinding().toInstance(
                new StripPrefixFilter(Arrays.asList("/passthrough/.*")));
    }
}
