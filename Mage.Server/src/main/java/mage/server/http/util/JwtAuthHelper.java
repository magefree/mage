package mage.server.http.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mage.server.User;
import mage.server.UserManager;
import mage.server.util.ConfigSettings;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JwtAuthHelper {

    public static final String JWT_SECRET_KEY = ConfigSettings.instance.getSigningKey();

    private static final Pattern JWT_REGEX = Pattern.compile("Bearer (.+?)");

    public static String mintJwt(String username) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, encodeKey("UTF-8"))
                .setSubject(username)
                .compact();
    }

    public static Optional<User> deriveUserFromJwt(String rawJwt) {
        Optional<Jws<Claims>> claims = extractJwtPayload(rawJwt);

        return UserManager.instance.getUserByName(claims.get().getBody().getSubject());
    }

    public static Optional<Jws<Claims>> extractJwtPayload(String rawJwt) {
        Jws<Claims> result = null;
        Matcher m = JWT_REGEX.matcher(rawJwt);

        if (m.matches()) {
            rawJwt = m.group(1);
            result = Jwts.parser()
                      .setSigningKey(encodeKey("UTF-8"))
                      .parseClaimsJws(rawJwt);
        }

        return Optional.ofNullable(result);
    }

    private static byte[] encodeKey(String rawKey) {
        byte[] key = null;

        try {
            key = JWT_SECRET_KEY.getBytes(rawKey);
        } catch(UnsupportedEncodingException ex) {

        }

        return key;
    }
}
