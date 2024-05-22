package org.mage.test.cards.single.onc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OtharriSunsGloryTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OtharriSunsGlory Otharri, Suns' Glory} {3}{R}{W}
     * Legendary Creature — Phoenix
     * Flying, lifelink, haste
     * Whenever Otharri, Suns’ Glory attacks, you get an experience counter. Then create a 2/2 red Rebel creature token that’s tapped and attacking for each experience counter you have.
     * {2}{R}{W}, Tap an untapped Rebel you control: Return Otharri from your graveyard to the battlefield tapped.
     * 3/3
     */
    private static final String otharri = "Otharri, Suns' Glory";

    private static void checkExperienceCounter(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCounters().getCount(CounterType.EXPERIENCE));
    }

    @Test
    public void test_Experience_Rebels() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, otharri);

        attack(1, playerA, otharri, playerB);
        runCode("experience count playerA after first attack", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (info, player, game) -> checkExperienceCounter(info, player, 1));

        attack(3, playerA, otharri, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3 * 2);
        assertLife(playerB, 20 - 3 * 2 - 2 * (1 + 2));
        assertPermanentCount(playerA, "Rebel Token", 1 + 2);
        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
    }
}
