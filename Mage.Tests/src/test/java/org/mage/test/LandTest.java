package org.mage.test;

import org.junit.Test;
import org.mage.test.base.MageAPI;
import org.mage.test.bdd.and.And;
import org.mage.test.bdd.given.Given;
import org.mage.test.bdd.then.Then;
import org.mage.test.bdd.when.When;

import static org.mage.test.base.MageAPI.Owner.*;

public class LandTest extends MageAPI {

    @Test
    public void testPlayingLandInMainPhase() throws Exception {
        Given.I.have.a.card("Mountain");
        And.phase.is("Precombat Main", mine);
        When.I.play("Mountain");
        Then.battlefield.has("Mountain");
        And.graveyards.empty();
    }

    /*@Test
    public void testLightningHelix() throws Exception {
        Given.I.have.a.card("Lightning Helix");
        And.battlefield.has("Mountain","Plains");
        And.phase.is("End of Turn", ai);
        And.lifes(20,20);
        When.I.play("Lightning Helix");
        Then.my.life(23);
        And.ai.life(17);
        And.my.graveyard.has("Lightning Helix");
        And.ai.graveyard.empty();
    }*/
}
