package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RaulTroubleShooterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RaulTroubleShooter Raul, Trouble Shooter} {1}{U}{B}
     * Legendary Creature — Zombie Mutant Rogue
     * Once during each of your turns, you may cast a spell from among cards in your graveyard that were milled this turn.
     * {T}: Each player mills a card. (They each put the top card of their library into their graveyard.)
     * 1/4
     */
    private static final String raul = "Raul, Trouble Shooter";

    @Test
    public void test_Cast_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, raul);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}: Each player");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears"); // Can cast from graveyard.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void test_Cast_Split() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, raul);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.LIBRARY, playerA, "Fire // Ice");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}: Each player");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ice", "Memnite"); // Can cast from graveyard.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1); // drawn from Ice
        assertGraveyardCount(playerA, "Fire // Ice", 1);
        assertTappedCount("Island", true, 2);
        assertTappedCount("Memnite", true, 1);
    }


    @Test
    public void test_Cast_Simple_Instant_YourTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, raul);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}: Each player");
        checkPlayableAbility("1: Can cast the turn milled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("2: Cannot cast opponent card", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        checkPlayableAbility("3: Cannot cast twice", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3);
        assertTappedCount("Mountain", true, 1);
    }

    @Test
    public void test_CannotCast_Instant_NotYourTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, raul);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        activateAbility(2, PhaseStep.UPKEEP, playerA, "{T}: Each player");
        checkPlayableAbility("1: Cannot cast on opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        checkPlayableAbility("2: Cannot cast not your card", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_CannotCast_OtherTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, raul);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}: Each player");

        checkPlayableAbility("Can cast the turn milled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("Cannot cast after that", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Cast_MilledBefore() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, raul);
        addCard(Zone.LIBRARY, playerA, "Bump in the Night"); // {B} Target opponent loses 3 life.
        addCard(Zone.LIBRARY, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Thought Scour"); // {U} Target player mills two cards. Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 6);

        castSpell(1, PhaseStep.UPKEEP, playerA, "Thought Scour", playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, raul, true);

        checkPlayableAbility("1: Can cast milled Bump in the Night", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bump in the Night", true);
        checkPlayableAbility("2: Can cast milled Memnite", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Memnite", true);
        checkPlayableAbility("3: Can not cast not milled Thought Scour", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thought Scour", false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bump in the Night", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("4: Can not cast milled Bump in the Night", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bump in the Night", false);
        checkPlayableAbility("5: Can not cast milled Memnite", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Memnite", false);
        checkPlayableAbility("6: Can not cast not milled Thought Scour", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thought Scour", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 3);
        assertLife(playerB, 20 - 3);
    }
}
