package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ExtractPowerTest extends CardTestPlayerBase {

    /**
     * Extract Power {5}{U}
     * Sorcery
     * Look at the top card of each player's library, then exile those cards face down. You may play them without
     * paying their mana costs for as long as they remain exiled.
     */
    private static final String extractPower = "Extract Power";

    @Test
    public void test_CastOwnAndOpponentsCardsWithoutPaying() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, extractPower); // {5}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G}
        //
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt"); // {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, extractPower);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // no mana left, both cast for free
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_PlayLand_AndCastOnLaterTurn() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, extractPower); // {5}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Forest");
        //
        addCard(Zone.LIBRARY, playerB, "Hill Giant"); // {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, extractPower);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");

        // for as long as it remains exiled, not only this turn
        checkPlayableAbility("opponent can't cast own exiled card", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Hill Giant", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void test_ModalDoubleFacedAndAdventureCards() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, extractPower); // {5}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Kazandu Mammoth"); // {1}{G}{G}, MDFC // Kazandu Valley
        //
        addCard(Zone.LIBRARY, playerB, "Bonecrusher Giant"); // {2}{R}, adventure // Stomp {1}{R}, 2 damage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, extractPower);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Kazandu Mammoth");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Stomp", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
        assertLife(playerB, 20 - 2);
    }
}
