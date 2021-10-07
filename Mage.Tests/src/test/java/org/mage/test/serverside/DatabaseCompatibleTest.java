package org.mage.test.serverside;

import mage.server.AuthorizedUser;
import mage.server.AuthorizedUserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Testing database compatible on new libs or updates.
 *
 * @author JayDi85
 */
public class DatabaseCompatibleTest {

    private final String JDBC_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test_AuthUsers() {
        try {
            // prepare test db
            String dbDir = tempFolder.newFolder().getAbsolutePath();
            String dbName = "users-db-sample.h2";
            String dbFullName = Paths.get(dbDir, dbName).toAbsolutePath().toString();
            String dbFullFileName = dbFullName + ".mv.db";
            Files.copy(
                    Paths.get("src", "test", "data", dbName + ".mv.db"),
                    Paths.get(dbFullFileName)
            );
            Assert.assertTrue(Files.exists(Paths.get(dbFullFileName)));

            AuthorizedUserRepository dbUsers = new AuthorizedUserRepository(
                    String.format(JDBC_URL, dbFullName)
            );

            // search
            Assert.assertNotNull(dbUsers.getByName("user1"));
            Assert.assertNotNull(dbUsers.getByEmail("user2@example.com"));
            Assert.assertNull(dbUsers.getByName("userFAIL"));

            // login
            AuthorizedUser user = dbUsers.getByName("user3");
            Assert.assertEquals("user name", user.getName(), "user3");
            Assert.assertTrue("user pas", user.doCredentialsMatch("user3", "pas3"));
            Assert.assertFalse("user wrong pas", user.doCredentialsMatch("user3", "123"));
            Assert.assertFalse("user empty pas", user.doCredentialsMatch("user3", ""));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    @Ignore // TODO: add records/stats db compatible test
    public void test_Records() {
    }
}
