package com.netflix.zuul.sample;

import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.filters.ZuulFilter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FiltersRegisteringService {

    private final List<ZuulFilter> filters;
    private final FilterRegistry filterRegistry;

    @Inject
    public FiltersRegisteringService(FilterRegistry filterRegistry, Set<ZuulFilter> filters) {
        this.filters = new ArrayList<>(filters);
        this.filterRegistry = filterRegistry;
    }

    public List<ZuulFilter> getFilters() {
        return filters;
    }

    @PostConstruct
    public void initialize() {
        for (ZuulFilter filter: filters) {
            this.filterRegistry.put(filter.filterName(), filter);
        }
    }
}
