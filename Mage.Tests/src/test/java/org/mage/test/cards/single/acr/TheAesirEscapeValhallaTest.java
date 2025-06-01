package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheAesirEscapeValhallaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheAesirEscapeValhalla The Aesir Escape Valhalla} {2}{G}
     * Enchantment — Saga
     * I — Exile a permanent card from your graveyard. You gain life equal to its mana value.
     * II — Put a number of +1/+1 counters on target creature you control equal to the mana value of the exiled card.
     * III — Return this Saga and the exiled card to their owner’s hand.
     */
    private static final String aesir = "The Aesir Escape Valhalla";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, aesir, 1);
        addCard(Zone.GRAVEYARD, playerA, "Gigantosaurus"); // 10/10 {G}{G}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aesir);
        setChoice(playerA, "Gigantosaurus");

        checkLife("after I, lifecount", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 + 5);
        checkExileCount("after I, exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gigantosaurus", 1);

        // turn 3
        addTarget(playerA, "Memnite");

        checkPermanentCounters("after II, +1/+1 counters", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", CounterType.P1P1, 5);

        // turn 5

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Gigantosaurus", 1);
        assertHandCount(playerA, aesir, 1);
    }
}
