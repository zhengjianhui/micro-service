package com.zjh.demo.user.client;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.alibaba.fastjson.JSON;
import com.zjh.demo.thrift.user.dto.UserDTO;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhengjianhui on 6/18/18
 */
public abstract class LoginFilter implements Filter {

    private static Cache<String, UserDTO> cache
            = CacheBuilder.newBuilder().maximumSize(10000).expireAfterAccess(3, TimeUnit.MINUTES).build();


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;

        String token = request1.getParameter("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request1.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }

        }

        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)) {
            userDTO = getUser(token);
        }

        if (userDTO == null) {
            response1.sendRedirect("http://127.0.0.1:8080/user/login");
            return;
        }
        login(request1, response1, userDTO);
        chain.doFilter(request, response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

    public void destroy() {

    }

    public UserDTO getUser(String token) {
        UserDTO userDTO = cache.getIfPresent(token);
        if(userDTO != null) {
            return userDTO;
        }

        String url = "http://127.0.0.1:8082/authentication";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("request user info failed statusLine" + httpResponse.getStatusLine().getStatusCode());
            }

            inputStream = httpResponse.getEntity().getContent();
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }

            userDTO = JSON.parseObject(sb.toString(), UserDTO.class);
            cache.put(token, userDTO);

            return userDTO;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                    // log
                }
            }
        }

        return null;
    }
}
