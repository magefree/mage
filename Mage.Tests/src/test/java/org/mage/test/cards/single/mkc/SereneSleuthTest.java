package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jmlundeen
 */
public class SereneSleuthTest extends CardTestPlayerBase {

    /*
    Serene Sleuth
    {1}{W}
    Creature - Human Detective
    When Serene Sleuth enters the battlefield, investigate.
    At the beginning of combat on your turn, investigate for each goaded creature you control. Then each creature you control is no longer goaded.
    2/2
    */
    private static final String sereneSleuth = "Serene Sleuth";

    /*
    Baeloth Barrityl, Entertainer
    {4}{R}
    Legendary Creature - Elf Shaman
    Creatures your opponents control with power less than Baeloth Barrityl's power are goaded.
    Whenever a goaded attacking or blocking creature dies, you create a Treasure token.
    Choose a Background
    2/5
    */
    private static final String baelothBarritylEntertainer = "Baeloth Barrityl, Entertainer";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard

    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";

    /*
    Cloudshift
    {W}
    Instant
    Exile target creature you control, then return that card to the battlefield under your control.
    */
    private static final String cloudshift = "Cloudshift";

    @Test
    public void testSereneSleuth() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, baelothBarritylEntertainer);
        addCard(Zone.BATTLEFIELD, playerA, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, sereneSleuth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sereneSleuth);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1 + 1);
        assertTrue("fugitive wizard is not goaded", getPermanent(fugitiveWizard).getGoadingPlayers().isEmpty());
    }

    @Test
    public void testSereneSleuthReGoaded() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, baelothBarritylEntertainer);
        addCard(Zone.BATTLEFIELD, playerA, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerA, sereneSleuth);
        addCard(Zone.HAND, playerB, cloudshift);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sereneSleuth);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, cloudshift, baelothBarritylEntertainer);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1 + 1 + 1);
        assertTrue("fugitive wizard is goaded", getPermanent(fugitiveWizard).getGoadingPlayers().isEmpty());
    }
}