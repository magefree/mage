package org.mage.test.cards.single.fic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SummonEsperValigarmandaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SummonEsperValigarmanda Summon: Esper Valigarmanda} {3}{R}
     * Enchantment Creature — Saga Drake
     * I — Exile an instant or sorcery card from each graveyard.
     * II, III, IV — Add {R} for each lore counter on this Saga. You may cast an instant or sorcery card exiled with this Saga, and mana of any type can be spent to cast that spell.
     * Flying, haste
     */
    private static final String esper = "Summon: Esper Valigarmanda";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, esper, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1);
        addCard(Zone.GRAVEYARD, playerA, "Shock", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, esper);
        setChoice(playerA, "Shock");
        setChoice(playerA, "Lightning Bolt");

        // turn 3
        setChoice(playerA, "Shock"); // choose to be cast
        setChoice(playerA, true); // choose to cast
        addTarget(playerA, playerB);

        checkLife("T3: after bolt life", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 2);

        // turn 5
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        checkLife("T5: no cast", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 2);

        // turn 7
        setChoice(playerA, "Lightning Bolt");
        setChoice(playerA, true); // choose to cast
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 - 3);
    }
}
