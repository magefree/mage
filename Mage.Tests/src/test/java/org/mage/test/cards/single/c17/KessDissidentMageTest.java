package org.mage.test.cards.single.c17;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class KessDissidentMageTest extends CardTestPlayerBase {

    // Kess, Dissident Mage
    // During each of your turns, you may cast an instant or sorcery card from your graveyard.
    // If a card cast this way would be put into your graveyard this turn, exile it instead.

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, "Kess, Dissident Mage", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        checkPlayableAbility("must play simple", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Split_OnePart() {
        addCard(Zone.BATTLEFIELD, playerA, "Kess, Dissident Mage", 1);
        //
        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.GRAVEYARD, playerA, "Alive // Well", 1); // {3}{G} // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        checkPlayableAbility("must play part", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Alive", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alive");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Centaur Token", 1);
        assertLife(playerA, 20);
        assertExileCount(playerA, "Alive // Well", 1);
    }

    @Test
    public void test_Split_Check() {
        // testing check command only for fused cards

        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.HAND, playerA, "Alive // Well", 1); // {3}{G} // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // tap green first for Alive spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 4);
        checkPlayableAbility("must play fused", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Alive // Well", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Alive // Well");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Centaur Token", 1);
        assertLife(playerA, 20 + 2);
        assertGraveyardCount(playerA, "Alive // Well", 1);
    }

    @Test
    public void test_Split_CantPlay() {
        addCard(Zone.BATTLEFIELD, playerA, "Kess, Dissident Mage", 1);
        //
        // Create a 3/3 green Centaur creature token.
        // You gain 2 life for each creature you control.
        addCard(Zone.GRAVEYARD, playerA, "Alive // Well", 1); // {3}{G} // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // tap green first for Alive spell
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 4);
        checkPlayableAbility("can't play fused from graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast fused Alive // Well", false);
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Alive // Well");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
