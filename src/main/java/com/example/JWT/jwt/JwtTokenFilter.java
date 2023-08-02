package com.example.JWT.jwt;

import com.example.JWT.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtTokenFilter extends OncePerRequestFilter
{

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
if(!hasAuthorizationHeader(request)){
    filterChain.doFilter(request,response);

    return;
}
        String accessToken = getAccessToken(request);
if(!jwtTokenUtil.validateAccessToken(accessToken)){
    filterChain.doFilter(request,response);

    return;
}

setAuthenticationContext(accessToken,request);
        filterChain.doFilter(request,response);



    }

    private boolean hasAuthorizationHeader(HttpServletRequest request){
        String header =request.getHeader("Authorization");

        if(ObjectUtils.isEmpty(header)|| !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }
    private String getAccessToken(HttpServletRequest httpServletRequest){
        String header =httpServletRequest.getHeader("Authorization");
        String token =header.split(" ")[1].trim();
        return token;

    }

    private UserDetails getUserdetails(String accessToken){
        User userDetails =new User();
        String[] subjectArray = jwtTokenUtil.getSubject(accessToken).split(",");

        userDetails.setEmail(subjectArray[1]);
        userDetails.setId(Integer.parseInt(subjectArray[0]));

        return userDetails;


    }

    private void setAuthenticationContext(String accessToken,HttpServletRequest request){
        UserDetails userdetails = getUserdetails(accessToken);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userdetails,null,null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


            SecurityContextHolder.getContext().setAuthentication( authenticationToken);

    }
}
