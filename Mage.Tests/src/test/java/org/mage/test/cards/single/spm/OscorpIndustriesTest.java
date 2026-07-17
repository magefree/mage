package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class OscorpIndustriesTest extends CardTestPlayerBase {

    /*
    Oscorp Industries
    
    Land
    This land enters tapped.
    When this land enters from a graveyard, you lose 2 life.
    {T}: Add {U}, {B}, or {R}.
    Mayhem
    */
    private static final String oscorpIndustries = "Oscorp Industries";

    /*
    Thought Courier
    {1}{U}
    Creature - Human Wizard
    {tap}: Draw a card, then discard a card.
    1/1
    */
    private static final String thoughtCourier = "Thought Courier";

    @Test
    public void testOscorpIndustries() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, oscorpIndustries);
        addCard(Zone.BATTLEFIELD, playerA, thoughtCourier);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");
        setChoice(playerA, oscorpIndustries);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, oscorpIndustries);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, oscorpIndustries, 1);
    }

    @Test
    public void testOscorpIndustriesNoMayhem() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, oscorpIndustries);
        addCard(Zone.BATTLEFIELD, playerA, thoughtCourier);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, oscorpIndustries);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, oscorpIndustries, 1);
    }

    @Test
    public void testCantPlayWithoutDiscard() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, oscorpIndustries);

        checkPlayableAbility("Can't play without discard", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Play " + oscorpIndustries, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, oscorpIndustries, 0);
    }

    @Test
    public void testOscorpIndustriesNextTurn() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, oscorpIndustries);
        addCard(Zone.BATTLEFIELD, playerA, thoughtCourier);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");
        setChoice(playerA, oscorpIndustries);

        checkPlayableAbility("Can't play without discard", 3, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Play " + oscorpIndustries, false);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, oscorpIndustries, 0);
    }
}