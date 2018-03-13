package mage.server;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.util.ByteSource;

@DatabaseTable(tableName = "authorized_user")
public class AuthorizedUser {

    @DatabaseField(id = true, indexName = "name_index", unique = true)
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

    @DatabaseField
    protected boolean active; // the user can't sign in

    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    protected Date lockedUntil; // the user can't sign in until timestamp

    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    protected Date chatLockedUntil; // the user can't use the chat until timestamp

    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    protected Date lastConnection; // time of the last user connect

    public AuthorizedUser() {
    }

    public AuthorizedUser(String name, Hash hash, String email) {
        this.name = name;
        this.password = hash.toBase64();
        this.salt = hash.getSalt().toBase64();
        this.hashAlgorithm = hash.getAlgorithmName();
        this.hashIterations = hash.getIterations();
        this.email = email;
        this.chatLockedUntil = null;
        this.active = true;
        this.lockedUntil = null;
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
