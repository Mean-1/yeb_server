package com.xxx.server.config.security.component;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Keafmd
 *
 * @ClassName: JwtTokenUtil
 * @Description: JwtToken工具类
 * @author: liuchen
 * @date: 2022/3/31 16:32
 * @Blog:
 */
@Component
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成Token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 通过Token获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            userName=claims.getSubject();
        } catch (Exception e) {
            userName=null;
        }
        return userName;
    }

    /**
     * 判断Token是否有效
     * @param token
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpried(token);
    }

    /**
     * 判断token能否被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpried(token);
    }

    /**
     * 刷新Token
     * @param token
     * @return
     */
    public String RefreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断Token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpried(String token) {
//        //根据token获取荷载
//        Claims claims = getClaimsFromToken(token);
        return getExpriedDateFromToken(token).before(new Date());
    }

    /**
     * 通过token获取失效时间
     * @param token
     * @return
     */
    private Date getExpriedDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从Token中获取荷载
     * @param token
     * @return
     */
    private  Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims=Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 根据荷载（claims）生成Token
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成Token失效时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }


}
