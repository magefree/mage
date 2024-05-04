package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KambalProfiteeringMayorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KambalProfiteeringMayor Kambal, Profiteering Mayor} {1}{W}{B}
     * Legendary Creature — Human Advisor
     * Whenever one or more tokens enter the battlefield under your opponents’ control, for each of them, create a tapped token that’s a copy of it. This ability triggers only once each turn.
     * Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
     * 2/4
     */
    private static final String kambal = "Kambal, Profiteering Mayor";

    @Test
    public void Test_KrenkosCommand() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, kambal);
        // Create two 1/1 red Goblin creature tokens.
        addCard(Zone.HAND, playerA, "Krenko's Command");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Krenko's Command");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 1); // Second Trigger happened
        assertLife(playerB, 20 + 1); // Second Trigger happened
        assertPermanentCount(playerA, "Goblin Token", 2);
        assertPermanentCount(playerB, "Goblin Token", 2);
    }

    @Test
    public void Test_BestialMenace() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, kambal);
        // Create a 1/1 green Snake creature token, a 2/2 green Wolf creature token, and a 3/3 green Elephant creature token.
        addCard(Zone.HAND, playerA, "Bestial Menace");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bestial Menace");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 1); // Second Trigger happened
        assertLife(playerB, 20 + 1); // Second Trigger happened
        assertPermanentCount(playerA, "Snake Token", 1);
        assertPermanentCount(playerA, "Wolf Token", 1);
        assertPermanentCount(playerA, "Elephant Token", 1);
        assertPermanentCount(playerB, "Snake Token", 1);
        assertPermanentCount(playerB, "Wolf Token", 0);     // TODO: this is a bug, should be 1, see #10811
        assertPermanentCount(playerB, "Elephant Token", 0); // TODO: this is a bug, should be 1, see #10811
    }

    @Test
    public void Test_Chatterfang() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, kambal);
        // Create two 1/1 red Goblin creature tokens.
        addCard(Zone.HAND, playerA, "Krenko's Command");
        // If one or more tokens would be created under your control, those tokens plus that many 1/1 green Squirrel creature tokens are created instead.
        addCard(Zone.BATTLEFIELD, playerA, "Chatterfang, Squirrel General");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Krenko's Command");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 1); // Second Trigger happened
        assertLife(playerB, 20 + 1); // Second Trigger happened
        assertPermanentCount(playerA, "Goblin Token", 2);
        assertPermanentCount(playerA, "Squirrel Token", 2);
        assertPermanentCount(playerB, "Goblin Token", 2);
        assertPermanentCount(playerB, "Squirrel Token", 2);
    }
}
