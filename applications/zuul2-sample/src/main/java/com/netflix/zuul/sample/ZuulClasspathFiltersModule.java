//package com.netflix.zuul.sample;
//
//import com.google.inject.AbstractModule;
//import com.google.inject.multibindings.Multibinder;
//import com.netflix.zuul.filters.ZuulFilter;
//import Healthcheck;
//import Debug;
//import DebugRequest;
//import Routes;
//import ZuulResponseFilter;
//
//
//public class ZuulClasspathFiltersModule extends AbstractModule {
//    @Override
//    protected void configure() {
//        Multibinder<ZuulFilter> filterMultibinder = Multibinder.newSetBinder(binder(), ZuulFilter.class);
//        filterMultibinder.addBinding().to(Healthcheck.class);
//        filterMultibinder.addBinding().to(Debug.class);
//        filterMultibinder.addBinding().to(DebugRequest.class);
//        filterMultibinder.addBinding().to(Routes.class);
//        filterMultibinder.addBinding().to(ZuulResponseFilter.class);
//        
//    }
//}
