package org.mage.test.cards.single.m3c;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class DesertWarfareTest extends CardTestPlayerBase {

    @Test
    public void testSacrificeDesertAndNonDesert() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Zuran Orb");
        addCard(Zone.BATTLEFIELD, playerA, "Dunes of the Dead");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a land");
        setChoice(playerA, "Dunes of the Dead");
        setChoice(playerA, "Whenever you sacrifice"); // order Desert Warfare + Dunes of the Dead triggers

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a land");
        setChoice(playerA, "Forest");

        // Still in graveyard during postcombat main before the end step
        checkPermanentCount("Desert not returned yet", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dunes of the Dead", 0);
        checkGraveyardCount("Desert in graveyard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dunes of the Dead", 1);
        checkGraveyardCount("Non-Desert in graveyard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Dunes of the Dead", 1);
        assertGraveyardCount(playerA, "Dunes of the Dead", 0);
        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 1);
    }

    @Test
    public void testSacrificeDesertOnOpponentTurnReturnsAtYourNextEndStep() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Zuran Orb");
        addCard(Zone.BATTLEFIELD, playerA, "Desert of the Indomitable");

        // Sacrifice Desert during opponent's turn (turn 2)
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a land");
        setChoice(playerA, "Desert of the Indomitable");

        // Should NOT return at opponent's end step (still in graveyard during turn 3 precombat main)
        checkPermanentCount("Still not returned on turn 3 main", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Desert of the Indomitable", 0);
        checkGraveyardCount("Still in graveyard on turn 3 main", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Desert of the Indomitable", 1);

        // Returns at the beginning of playerA's next end step (end of turn 3)
        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Desert of the Indomitable", 1);
        assertGraveyardCount(playerA, "Desert of the Indomitable", 0);
    }

    @Test
    public void testDestroyedDesertDoesNotReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Desert of the Indomitable");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Sinkhole");

        // Sinkhole destroys target land (not sacrificed, not from hand/library)
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sinkhole", "Desert of the Indomitable");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Desert of the Indomitable", 0);
        assertGraveyardCount(playerA, "Desert of the Indomitable", 1);
    }

    @Test
    public void testDiscardDesertAndNonDesert() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");
        addCard(Zone.HAND, playerA, "Desert of the Indomitable");
        addCard(Zone.HAND, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card");
        setChoice(playerA, "Desert of the Indomitable");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card");
        setChoice(playerA, "Forest");

        checkPermanentCount("Discarded Desert not returned yet", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Desert of the Indomitable", 0);
        checkGraveyardCount("Discarded Desert in graveyard", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Desert of the Indomitable", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Desert of the Indomitable", 1);
        assertGraveyardCount(playerA, "Desert of the Indomitable", 0);
        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 1);
    }

    @Test
    public void testMillMultipleDesertsAndNonDeserts() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Tome Scour"); // Target player mills five cards.

        addCard(Zone.LIBRARY, playerA, "Desert of the Indomitable");
        addCard(Zone.LIBRARY, playerA, "Desert of the Mindful");
        addCard(Zone.LIBRARY, playerA, "Desert of the Fervent");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tome Scour", playerA);
        // 3 triggers when 3 Deserts are milled simultaneously, and 3 delayed triggers at end step
        setChoice(playerA, "Whenever you sacrifice", 2);
        setChoice(playerA, "At the beginning of your next end step", 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Desert of the Indomitable", 1);
        assertPermanentCount(playerA, "Desert of the Mindful", 1);
        assertPermanentCount(playerA, "Desert of the Fervent", 1);
        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testCombatTokensFourVsFiveDeserts() {
        addCard(Zone.BATTLEFIELD, playerA, "Desert Warfare");
        addCard(Zone.BATTLEFIELD, playerA, "Desert of the Indomitable", 4);
        addCard(Zone.HAND, playerA, "Desert of the Mindful");

        // Turn 1 combat: only 4 Deserts -> 0 tokens
        checkPermanentCount("No tokens with 4 Deserts", 1, PhaseStep.DECLARE_ATTACKERS, playerA, "Sand Warrior Token", 0);

        // Play 5th Desert in postcombat main
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Desert of the Mindful");

        // Turn 3 combat: 5 Deserts -> 5 Sand Warrior tokens with haste
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Sand Warrior Token", 5);
        assertAbility(playerA, "Sand Warrior Token", HasteAbility.getInstance(), true, 5);
    }
}
