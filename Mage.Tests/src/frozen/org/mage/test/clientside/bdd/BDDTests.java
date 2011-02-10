package org.mage.test.clientside.bdd;

import org.junit.Test;
import org.mage.test.clientside.base.Command;
import org.mage.test.clientside.base.MageAPI;
import org.mage.test.clientside.base.exception.CardNotFoundException;
import org.mage.test.clientside.bdd.and.And;
import org.mage.test.clientside.bdd.given.Given;
import org.mage.test.clientside.bdd.then.Then;
import org.mage.test.clientside.bdd.when.When;

import static org.mage.test.clientside.base.MageAPI.Owner.mine;

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
