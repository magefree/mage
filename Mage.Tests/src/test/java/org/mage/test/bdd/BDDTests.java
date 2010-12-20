package org.mage.test.bdd;

import org.junit.Test;
import org.mage.test.base.Command;
import org.mage.test.base.MageAPI;
import org.mage.test.base.exception.CardNotFoundException;
import org.mage.test.bdd.and.And;
import org.mage.test.bdd.given.Given;
import org.mage.test.bdd.then.Then;
import org.mage.test.bdd.when.When;

import static org.mage.test.base.MageAPI.Owner.mine;

/**
 * Tests BDD classes.
 */
public class BDDTests extends MageAPI {

    @Test
    public void testNonExistingCard() throws Exception {
        Expect.expect(CardNotFoundException.class, new Command() {
            @Override
            public void execute() throws Exception {
                Given.I.have.a.card("Super Puper Card");
                And.phase.is("Precombat Main", mine);
                When.I.play("Super Puper Card");
                Then.battlefield.has("Mountain");
                And.graveyards.empty();
            }
        });
    }
}
