package com.steinf.springbootfeigncorrelationid;

import org.slf4j.MDC;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import static com.steinf.springbootfeigncorrelationid.CorrelationIdFilter.HEADER_KEY;

public class FeignCorrelationIdRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate template) {
    String cid = MDC.get(HEADER_KEY);
    if (cid != null) {
      template.header(HEADER_KEY, cid);
    }
  }
}
