package org.mage.test.clientside.base;

import mage.Constants;
import org.mage.test.clientside.bdd.and.And;
import org.mage.test.clientside.bdd.given.Given;
import org.mage.test.clientside.bdd.then.Then;
import org.mage.test.clientside.bdd.when.When;

import static org.mage.test.clientside.base.MageAPI.Owner.mine;

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
