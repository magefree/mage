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
    }

    private static final String unicorn = "Lonesome Unicorn"; // 4W 3/3 with adventure 2W 2/2 token
    private static final String rider = "Rider in Need";
    private static final String lifegain = "Chaplain's Blessing";
    private static final String kess = "Kess, Dissident Mage";
    // Once during each of your turns, you may cast an instant or sorcery spell from your graveyard.
    // If a spell cast this way would be put into your graveyard, exile it instead.

    @Test
    public void testKessCastAdventure() {
        addCard(Zone.BATTLEFIELD, playerA, kess);
        addCard(Zone.GRAVEYARD, playerA, lifegain);
        addCard(Zone.GRAVEYARD, playerA, unicorn);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        checkPlayableAbility("lifegain", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lifegain, true);
        checkPlayableAbility("creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + unicorn, false);
        checkPlayableAbility("adventure", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + rider, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rider);

        checkPlayableAbility("already used", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + lifegain, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Knight Token", 1);
        assertExileCount(playerA, unicorn, 1);
    }

    @Test
    public void testKessCastAdventureAfterDeath() {
        addCard(Zone.BATTLEFIELD, playerA, kess);
        addCard(Zone.GRAVEYARD, playerA, lifegain);
        addCard(Zone.HAND, playerA, unicorn);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Bairn"); // for sacrificing creature
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, unicorn);

        checkPlayableAbility("lifegain", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + lifegain, true);
        checkPlayableAbility("creature", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + unicorn, false);
        checkPlayableAbility("adventure", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + rider, false);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice another");
        setChoice(playerA, unicorn);

        checkGraveyardCount("sacrificed", 2, PhaseStep.PRECOMBAT_MAIN, playerA, unicorn, 1);

        checkPlayableAbility("lifegain", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + lifegain, true);
        checkPlayableAbility("creature", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + unicorn, false);
        checkPlayableAbility("adventure", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + rider, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, rider);
        setChoice(playerA, "Kess, Dissident Mage"); // Test sees 2 ways to cast the Adventure, actual game only shows the one.

        checkPlayableAbility("already used", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + lifegain, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Knight Token", 1);
        assertExileCount(playerA, unicorn, 1);
    }

}
