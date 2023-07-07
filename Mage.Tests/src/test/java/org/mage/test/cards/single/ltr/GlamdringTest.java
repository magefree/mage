package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.fail;

public class GlamdringTest extends CardTestPlayerBase {
    void setUp() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Healing Salve");
        addCard(Zone.HAND, playerA, "In Garruk's Wake");
        addCard(Zone.HAND, playerA, "Blue Sun's Zenith");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Glamdring");
        addCard(Zone.BATTLEFIELD, playerA, "Blur Sliver"); // 2/2, Haste

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Blur Sliver");
    }
    @Test
    public void tooExpensiveToCastForFree() {
        setUp();

        attack(1, playerA, "Blur Sliver");
        setChoice(playerA, "In Garruk's Wake"); // 9 mana, so we shouldn't be able to choose it
        setChoice(playerA, "Yes");

        try {
            setStopAt(1, PhaseStep.FIRST_COMBAT_DAMAGE);
            execute();
        }
        catch (AssertionError e) {
            assert(e.getMessage().contains("Missing CHOICE def for turn 1, step FIRST_COMBAT_DAMAGE, PlayerA"));
            return;
        }
        fail("Was able to pick [[In Garruk's Wake]] but it costs more than 2");
    }

    @Test
    public void canCastForFree() {
        setUp();

        attack(1, playerA, "Blur Sliver");
        setChoice(playerA, "Lightning Bolt"); // 1 mana, so we should be able to choose it
        setChoice(playerA, "Yes");
        addTarget(playerA, playerB); // target player

        setStopAt(1, PhaseStep.FIRST_COMBAT_DAMAGE);
        execute();

        // Lightning Bolt did 3 damage
        assertLife(playerB, 20 - 2 - 3);
    }

    @Test
    public void enoughCardsInGraveyardToCastInGarruksWake() {
        setUp();

        addCard(Zone.GRAVEYARD, playerA, "Serum Visions", 7);
        addCard(Zone.GRAVEYARD, playerA, "Brainstorm", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Elder Deep-Fiend", 2, true);
        attack(1, playerA, "Blur Sliver");
        setChoice(playerA, "In Garruk's Wake"); // We now do enough damage to cast it
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.FIRST_COMBAT_DAMAGE);
        execute();

        // Equipped creature gets +1/+0 for each instant and sorcery card in your graveyard
        assertLife(playerB, 20 - 2 - (7 + 5));

        // In Garruk's Wake destroyed our opponent's creatures
        assertPermanentCount(playerB, "Elder Deep-Fiend", 0);
    }
}