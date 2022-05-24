package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DeepCrimson
 */
public class ExileFromGraveyardTest extends CardTestPlayerBase {

    /**
     * Unlicensed Hearse - It can only exile cards from one graveyard per activation
     */
    @Test
    public void testCannotExileFromTwoGraveyardsWithUnlicensedHearse() {
        addCard(Zone.BATTLEFIELD, playerA, "Unlicensed Hearse");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerB, "Salt Flats");
        setStrictChooseMode(true);
        execute();

//      Each graveyard has 1 card. Unlicensed Hearse hasn't exiled any cards, so it's a 0/0.
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerA, 1);
        assertPowerToughness(playerA, "Unlicensed Hearse", 0, 0);

        activateAbility(
                1,
                PhaseStep.PRECOMBAT_MAIN,
                playerA,
                "{T}: Exile up to two target cards from a single graveyard.",
                new String[]{"Salt Flats", "Grizzly Bears"}
        );
//        try {
        execute();
        assertAllCommandsUsed();

//            Assert.fail("must throw exception on execute");
//        } catch (Throwable e) {
//            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
//                Assert.fail("Expected error about PlayerA actions, but got:\n" + e.getMessage());
//            }
//        }


        // Activation failed since tried to exile from two graveyards. Nothing has changed
        // since earlier.
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerA, 1);
        assertPowerToughness(playerA, "Unlicensed Hearse", 0, 0);
    }

    @Test
    public void testExileFromOneGraveyardWithUnlicensedHearse() {
        addCard(Zone.BATTLEFIELD, playerA, "Unlicensed Hearse");
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears");
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerB, 2);
        assertPowerToughness(playerA, "Unlicensed Hearse", 0, 0);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{T}: Exile up to two target cards from a single graveyard.",
                new String[]{"Grizzly Bears", "Grizzly Bears"}
        );

        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, 0);
        assertPowerToughness(playerA, "Unlicensed Hearse", 2, 2);
    }
}
