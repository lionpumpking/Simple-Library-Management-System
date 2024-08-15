//package com.myproject.my_demo.demos.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.util.pattern.PathPatternParser;
//
///**
// * Description:
// * Author:
// * Data:
// */
//@Configuration
//public class CorsConfig  {
//
//   public CorsFilter addCorsMappings(CorsRegistry registry) {
//      CorsConfiguration config = new CorsConfiguration();
//      config.addAllowedOrigin("*");
//      config.addAllowedHeader("*");
//      config.addAllowedMethod("*");
//      config.setAllowCredentials(true);
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//      source.registerCorsConfiguration("/**", config);
//      return new CorsFilter(source);
//
//
//      // 设置允许跨域的路径
////      registry.addMapping("/**")
////              // 设置允许跨域请求的域名
////              .allowedOriginPatterns("*")
////              // 是否允许cookie
////              .allowCredentials(true)
////              // 设置允许的请求方式
////              .allowedMethods("GET", "POST", "DELETE", "PUT")
////              // 设置允许的header属性
////              .allowedHeaders("*")
////              // 跨域允许时间
////              .maxAge(3600);
//   }
//}
