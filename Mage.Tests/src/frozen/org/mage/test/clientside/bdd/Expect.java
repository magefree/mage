package org.mage.test.clientside.bdd;

import org.junit.Assert;
import org.mage.test.clientside.base.Command;

import static org.hamcrest.CoreMatchers.is;

/**
 * Asserts expecting exception.
 *
 * @author nantuko
 */
public class Expect {
    public static void expect(Class<? extends RuntimeException> t, Command command) {
        try {
            command.execute();
        } catch (Throwable e) {
            Assert.assertThat(t.getName(), is(e.getClass().getName()));
            return;
        }
        throw new AssertionError("Expected exception wasn't thrown: " + t.getName());
    }
}
