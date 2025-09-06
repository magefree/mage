package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class GwenStacyTest extends CardTestPlayerBase {

    /*
    Gwen Stacy
    {1}{R}
    Legendary Creature - Human Performer Hero
    When Gwen Stacy enters, exile the top card of your library. You may play that card for as long as you control this creature.
    {2}{U}{R}{W}: Transform Gwen Stacy. Activate only as a sorcery.
    2/1
    Ghost-Spider
    {2}{U}{R}{W}
    Legendary Creature - Spider Human Hero
    Flying, vigilance, haste
    Whenever you play a land from exile or cast a spell from exile, put a +1/+1 counter on Ghost-Spider.
    Remove two counters from Ghost-Spider: Exile the top card of your library. You may play that card this turn.
    4/4

    */
    private static final String gwenStacy = "Gwen Stacy";
    private static final String ghostSpider = "Ghost-Spider";

    @Test
    @Ignore("Enable after transform mdfc rework")
    public void testGhostSpider() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, gwenStacy);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ghostSpider, true);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, ghostSpider, CounterType.P1P1, 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, ghostSpider, CounterType.CHARGE, 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, ghostSpider, CounterType.P0P1, 1);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Remove two counters");
        setChoiceAmount(playerA, 1); // Charge
        setChoiceAmount(playerA, 1); // P0P1
        setChoiceAmount(playerA, 0); // P1P1
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mountain");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, ghostSpider, 4 + 1 + 1, 4 + 1 + 1);
    }
}