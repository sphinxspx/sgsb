package com.zjhc.sgsb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * create by 2020.4.23
 * 解决浏览器跨域问题，当从一个端口地址访问另一个端口地址时会产生这个问题
 *
 * 解决方式一：使用过滤器
 * 方式二：
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
//    @Bean
//    public FilterRegistrationBean corsFilter(){
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        //设置你要允许的网站域名，*表示任意域名
//        //config.addAllowedOrigin("http://127.0.0.1");
//        config.addAllowedOrigin("*");
//        //表示获取任意头部信息
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("GET,POST,PUT,DELETE,HEAD,OPTIONS");
//        //设置跨域路劲，/**表示所有路径都能跨域
//        source.registerCorsConfiguration("/**",config);
//        //把前面配置放到过滤器
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        //这个设置很重要，为避免麻烦，请设置在最前面
//        //过滤器有很多个，把这个放第一个。过滤器在浏览器和servlet之间，可以有多个。
//        bean.setOrder(0);
//        return bean;
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/*")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE","PATCH")
                .allowedHeaders("*")
                //设置是否可以携带cookie
                .allowCredentials(true)
                .maxAge(3600);
    }
}
