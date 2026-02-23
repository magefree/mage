package org.mage.test.cards.single.lcc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class EyeOfOjerTaqTest extends CardTestPlayerBase {

    /*
    Eye of Ojer Taq
    {3}
    Artifact
    {T}: Add one mana of any color.
    Craft with two that share a card type {6}

    Apex Observatory
    Artifact
    Apex Observatory enters the battlefield tapped. As it enters, choose a card type shared among two exiled cards used to craft it.
    {T}: The next spell you cast this turn of the chosen type can be cast without paying its mana cost.
    */
    private static final String eyeOfOjerTaq = "Eye of Ojer Taq";
    private static final String apexObservatory = "Apex Observatory";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";

    /*
    Ponder
    {U}
    Sorcery
    Look at the top three cards of your library, then put them back in any order. You may shuffle your library.
    Draw a card.
    */
    private static final String ponder = "Ponder";

    /*
    Shock
    {R}
    Instant
    Shock deals 2 damage to any target.
    */
    private static final String shock = "Shock";

    @Test
    public void testEyeOfOjerTaq() {
        addCard(Zone.BATTLEFIELD, playerA, eyeOfOjerTaq);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.GRAVEYARD, playerA, lightningBolt);
        addCard(Zone.GRAVEYARD, playerA, shock);
        addCard(Zone.HAND, playerA, shock, 2);
        addCard(Zone.HAND, playerA, ponder);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with two that share a card type");
        addTarget(playerA, lightningBolt + "^" + shock);
        setChoice(playerA, "Instant");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, shock, playerB); // should be able to cast for free
        setChoice(playerA, "Cast without paying");
        checkPlayableAbility("Can't cast second shock", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Shock", false);

        checkPlayableAbility("Can't cast ponder", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ponder", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2); // took 2 damage from shock
        assertExileCount(playerA, lightningBolt, 1);
        assertExileCount(playerA, shock, 1);
        assertGraveyardCount(playerA, shock, 1);
        assertPermanentCount(playerA, apexObservatory, 1);
    }
}