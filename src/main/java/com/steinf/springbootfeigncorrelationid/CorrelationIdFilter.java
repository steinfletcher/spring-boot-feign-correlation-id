package com.steinf.springbootfeigncorrelationid;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
public class CorrelationIdFilter implements Filter {
  public static final String HEADER_KEY = "correlation_id";

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    if (req instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest) req;
      String requestCid = request.getHeader(HEADER_KEY);
      if (requestCid != null) {
        MDC.put(HEADER_KEY, requestCid);
      }
    }

    try {
      chain.doFilter(req, res);
    } finally {
      MDC.remove(HEADER_KEY);
    }
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig fc) {}
}
