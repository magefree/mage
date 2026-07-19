package org.mage.test.serverside;

import mage.server.AuthorizedUser;
import mage.server.AuthorizedUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Testing database compatible on new libs or updates.
 *
 * @author JayDi85
 */
public class DatabaseCompatibleTest {

    @TempDir
    Path tempFolder;


    @Test
    public void test_AuthUsers() {
        try {
            // prepare test db
            String dbDir = tempFolder.toFile().getAbsolutePath();
            String dbName = "users-db-sample.h2";
            String dbFullName = Paths.get(dbDir, dbName).toAbsolutePath().toString();
            String dbFullFileName = dbFullName + ".mv.db";
            Files.copy(
                    Paths.get("src", "test", "data", dbName + ".mv.db"),
                    Paths.get(dbFullFileName)
            );
            Assertions.assertTrue(Files.exists(Paths.get(dbFullFileName)));

            String connectionString = String.format("jdbc:h2:file:%s;AUTO_SERVER=TRUE", dbFullName);
            AuthorizedUserRepository dbUsers = new AuthorizedUserRepository(connectionString);

            // search
            Assertions.assertNotNull(dbUsers.getByName("user1"));
            Assertions.assertNotNull(dbUsers.getByEmail("user2@example.com"));
            Assertions.assertNull(dbUsers.getByName("userFAIL"));

            // login
            AuthorizedUser user = dbUsers.getByName("user3");
            Assertions.assertEquals(user.getName(), "user3", "user name");
            Assertions.assertTrue(user.doCredentialsMatch("user3", "pas3"), "user pas");
            Assertions.assertFalse(user.doCredentialsMatch("user3", "123"), "user wrong pas");
            Assertions.assertFalse(user.doCredentialsMatch("user3", ""), "user empty pas");
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Disabled // TODO: add records/stats db compatible test
    public void test_Records() {
    }
}
