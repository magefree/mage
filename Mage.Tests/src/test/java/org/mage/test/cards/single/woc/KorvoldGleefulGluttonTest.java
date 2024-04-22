package org.mage.test.cards.single.woc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

public class KorvoldGleefulGluttonTest extends CardTestPlayerBase {
    
    /** 
        This spell costs {1} less to cast for each card type among permanents you’ve sacrificed this turn.
        Flying, trample, haste
        Whenever Korvold deals combat damage to a player, put X +1/+1 counters on Korvold and draw X cards, where X is the number of permanent types among cards in your graveyard.
     */
    private final String KORVOLD = "Korvold, Gleeful Glutton";
    private final String OVEN = "Witch's Oven";
    private final String CAT = "Cauldron Familiar";
    private final String DOG = "Spirited Companion";

    @Test
    public void testEffects() {
        addCard(Zone.HAND, playerA, KORVOLD);
        addCard(Zone.BATTLEFIELD, playerA, OVEN);
        addCard(Zone.BATTLEFIELD, playerA, DOG);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.GRAVEYARD, playerA, CAT);

        // Sacrifice dog to oven
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        setChoice(playerA, DOG);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Sacrifice food to cat
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a Food");
        setChoice(playerA, "Food Token");

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Korvold should be reduced by 3 (enchantment, creature, artifact sacrificed)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, KORVOLD);

        // Korvold attacks, effect should trigger for X=2 because of dog
        attack(1, playerA, KORVOLD);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(KORVOLD, CounterType.P1P1, 2);
        assertHandCount(playerA, 2);
    }
}
