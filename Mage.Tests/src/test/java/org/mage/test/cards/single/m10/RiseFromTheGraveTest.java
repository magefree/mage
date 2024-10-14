package org.mage.test.cards.single.m10;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class RiseFromTheGraveTest extends CardTestPlayerBase {

    private static final String rise = "Rise from the Grave";
    // Put target creature card from a graveyard onto the battlefield under your control.
    // That creature is a black Zombie in addition to its other colors and types.

    private static final String looter = "Merfolk Looter";
    private static final String unsummon = "Unsummon";
    private static final String unconventionalTactics = "Unconventional Tactics";
    // Whenever a Zombie enters the battlefield under your control, you may pay {W}. If you do, return Unconventional Tactics from your graveyard to your hand.
    private static final String direUndercurrents = "Dire Undercurrents";
    // Whenever a blue creature enters the battlefield under your control, you may have target player draw a card.
    // Whenever a black creature enters the battlefield under your control, you may have target player discard a card.
    private static final String kraken = "Kraken Hatchling";

    /*
     * Related ruling for Chainer, Dementia Master
     * As it enters the battlefield, it is already a black Nightmare (and perhaps some other creature types);
     * it doesn't enter and then become a black Nightmare. (2022-12-08)
     */

    @Test
    public void testGainedCharacteristicsRespectZCC() {
        addCard(Zone.GRAVEYARD, playerB, looter);
        addCard(Zone.HAND, playerA, rise);
        addCard(Zone.HAND, playerB, unsummon);
        addCard(Zone.HAND, playerA, kraken);
        addCard(Zone.BATTLEFIELD, playerA, direUndercurrents);
        addCard(Zone.GRAVEYARD, playerA, unconventionalTactics);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rise, looter);
        setChoice(playerA, "Whenever a blue"); // order triggers
        addTarget(playerA, playerB); // to draw
        setChoice(playerA, "Whenever a black"); // order triggers
        addTarget(playerA, playerA); // to discard
        setChoice(playerA, true); // pay {W}
        setChoice(playerA, true); // to discard
        setChoice(playerA, kraken); // discarded
        setChoice(playerA, false); // no draw

        checkPT("looter", 1, PhaseStep.BEGIN_COMBAT, playerA, looter, 1, 1);
        checkColor("looter", 1, PhaseStep.BEGIN_COMBAT, playerA, looter, "U", true);
        checkColor("looter", 1, PhaseStep.BEGIN_COMBAT, playerA, looter, "B", true);
        checkSubType("looter", 1, PhaseStep.BEGIN_COMBAT, playerA, looter, SubType.MERFOLK, true);
        checkSubType("looter", 1, PhaseStep.BEGIN_COMBAT, playerA, looter, SubType.ZOMBIE, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, unsummon, looter);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, looter);

        checkPT("looter2", 2, PhaseStep.BEGIN_COMBAT, playerB, looter, 1, 1);
        checkColor("looter2", 2, PhaseStep.BEGIN_COMBAT, playerB, looter, "U", true);
        checkColor("looter2", 2, PhaseStep.BEGIN_COMBAT, playerB, looter, "B", false);
        checkSubType("looter2", 2, PhaseStep.BEGIN_COMBAT, playerB, looter, SubType.MERFOLK, true);
        checkSubType("looter2", 2, PhaseStep.BEGIN_COMBAT, playerB, looter, SubType.ZOMBIE, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertGraveyardCount(playerA, rise, 1);
        assertGraveyardCount(playerB, unsummon, 1);
        assertHandCount(playerA, unconventionalTactics, 1);
        assertGraveyardCount(playerA, kraken, 1);
    }

}
