package org.mage.test.base;

import mage.Constants;
import org.junit.BeforeClass;
import org.mage.test.bdd.StepController;
import org.mage.test.bdd.StepState;
import org.mage.test.bdd.and.And;
import org.mage.test.bdd.given.Given;
import org.mage.test.bdd.then.Then;
import org.mage.test.bdd.when.When;

import static org.mage.test.base.MageAPI.Owner.mine;

/**
 * Contains wrappers for bdd calls.
 */
public class MageAPIExtended extends  MageAPI {

    public void addCard(String cardName, Constants.Zone zone) throws Exception {
        Given.I.have.a.card("Mountain");
    }

    public void setPhase(String phase, Owner owner) throws Exception {
        And.phase.is("Precombat Main", mine);
    }

    public void play(String cardName) throws Exception {
        When.I.play("Mountain");
    }

    public void assertBattlefield(String cardName) throws Exception {
        Then.battlefield.has("Mountain");
    }

    public void assertGraveyardsCount(int count) throws Exception {
        And.graveyards.empty();
    }
}
