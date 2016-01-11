package mage.server;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Hash;

@DatabaseTable(tableName = "authorized_user")
public class AuthorizedUser {

    @DatabaseField(indexName = "name_index", unique = true)
    protected String name;

    @DatabaseField
    protected String password;

    @DatabaseField
    protected String salt;

    @DatabaseField
    protected String hashAlgorithm;

    @DatabaseField
    protected int hashIterations;

    @DatabaseField(indexName = "email_index", unique = true)
    protected String email;

    public AuthorizedUser() {
    }

    public AuthorizedUser(String name, Hash hash, String email) {
        this.name = name;
        this.password = hash.toBase64();
        this.salt = hash.getSalt().toBase64();
        this.hashAlgorithm = hash.getAlgorithmName();
        this.hashIterations = hash.getIterations();
        this.email = email;
    }

    public boolean doCredentialsMatch(String name, String password) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(this.hashAlgorithm);
        matcher.setHashIterations(this.hashIterations);
        AuthenticationToken token = new UsernamePasswordToken(name, password);
        AuthenticationInfo info = new SimpleAuthenticationInfo(this.name,
                ByteSource.Util.bytes(Base64.decode(this.password)),
                ByteSource.Util.bytes(Base64.decode(this.salt)), "");
        return matcher.doCredentialsMatch(token, info);
    }

    public String getName() {
        return this.name;
    }
}
