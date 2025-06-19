package org.mage.test.cards.single.tdc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BetorAncestorsVoiceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BetorAncestorsVoice Betor, Ancestor's Voice} {2}{W}{B}{G}
     * Legendary Creature — Spirit Dragon
     * Flying, lifelink
     * At the beginning of your end step, put a number of +1/+1 counters on up to one other target creature you control equal to the amount of life you gained this turn. Return up to one target creature card with mana value less than or equal to the amount of life you lost this turn from your graveyard to the battlefield.
     * 3/5
     */
    private static final String betor = "Betor, Ancestor's Voice";

    // Bug Report: If you got Betor and Rhox Faithmender on the field, and attack with both.
    // The gui shows you got the 8 life you are supposed to get, but in the end of turn trigger,
    // there will be only 4 counters put on a creature.
    @Test
    public void test_RhowFaithmender() {
        addCard(Zone.BATTLEFIELD, playerA, betor, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Rhox Faithmender", 1);

        attack(1, playerA, betor, playerB);
        attack(1, playerA, "Rhox Faithmender", playerB);

        addTarget(playerA, "Rhox Faithmender"); // Betor trigger's first target
        addTarget(playerA, TestPlayer.TARGET_SKIP); // second target

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 8);
        assertCounterCount(playerA, "Rhox Faithmender", CounterType.P1P1, 8);
    }
}
