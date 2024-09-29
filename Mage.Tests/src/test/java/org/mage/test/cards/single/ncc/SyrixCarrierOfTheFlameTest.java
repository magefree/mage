package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SyrixCarrierOfTheFlameTest extends CardTestPlayerBase {

    private static final String syrix = "Syrix, Carrier of the Flame"; // 3/3
    // At the beginning of each end step, if a creature card left your graveyard this turn,
    //      target Phoenix you control deals damage equal to its power to any target.
    // Whenever another Phoenix you control dies, you may cast Syrix, Carrier of the Flame from your graveyard.
    private static final String phoenix = "Firewing Phoenix"; // 4/2
    private static final String shock = "Shock";
    private static final String historian = "Illustrious Historian";
    // {5}, Exile Illustrious Historian from your graveyard: Create a tapped 3/2 red and white Spirit creature token.

    @Test
    public void testDamageTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, syrix);
        addCard(Zone.BATTLEFIELD, playerA, phoenix);
        addCard(Zone.GRAVEYARD, playerA, historian);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}, Exile ");

        checkExileCount("exiled", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, historian, 1);
        checkPT("token", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Spirit Token", 3, 2);
        checkLife("before trigger", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        checkLife("before trigger", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, 20);

        addTarget(playerA, phoenix); // target Phoenix
        addTarget(playerA, playerB); // deals damage

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, syrix, 3, 3);
        assertPowerToughness(playerA, phoenix, 4, 2);

    }

    @Test
    public void testDamageTriggerOpponentSource() {
        String cremate = "Cremate"; // {B} Exile target card from a graveyard. Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, syrix);
        addCard(Zone.BATTLEFIELD, playerA, phoenix);
        addCard(Zone.GRAVEYARD, playerA, historian);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, cremate);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, cremate, historian);

        checkExileCount("exiled", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, historian, 1);
        checkLife("before trigger", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, 20);
        checkLife("before trigger", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, 20);

        addTarget(playerA, phoenix); // target Phoenix
        addTarget(playerA, playerB); // deals damage

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);
        assertPowerToughness(playerA, syrix, 3, 3);
        assertPowerToughness(playerA, phoenix, 4, 2);

    }

    @Test
    public void testCast() {
        addCard(Zone.GRAVEYARD, playerA, syrix);
        addCard(Zone.BATTLEFIELD, playerA, phoenix);
        addCard(Zone.HAND, playerA, shock);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shock, phoenix);
        // phoenix dies, syrix ability triggers
        setChoice(playerA, true); // yes to cast

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, syrix, 3, 3);
        assertGraveyardCount(playerA, phoenix, 1);
        assertGraveyardCount(playerA, shock, 1);
        assertTappedCount("Badlands", true, 5);

    }

}
