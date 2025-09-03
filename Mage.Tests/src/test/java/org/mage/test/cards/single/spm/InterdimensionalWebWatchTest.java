package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class InterdimensionalWebWatchTest extends CardTestPlayerBase {

    /*
    Interdimensional Web Watch
    {4}
    Artifact
    When this artifact enters, exile the top two cards of your library. Until the end of your next turn, you may play those cards.
    {T}: Add two mana in any combination of colors. Spend this mana only to cast spells from exile.
    */
    private static final String interdimensionalWebWatch = "Interdimensional Web Watch";

    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
    */
    private static final String lightningBolt = "Lightning Bolt";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard
    
    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";

    /*
    Shock
    {R}
    Instant
    Shock deals 2 damage to any target.
    */
    private static final String shock = "Shock";

    @Test
    public void testInterdimensionalWebWatch() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, lightningBolt);
        addCard(Zone.LIBRARY, playerA, fugitiveWizard);
        addCard(Zone.HAND, playerA, interdimensionalWebWatch);
        addCard(Zone.HAND, playerA, shock);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, interdimensionalWebWatch, true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add two");
        setChoiceAmount(playerA, 0, 1, 0, 1, 0); // Add {U}{R}
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPlayableAbility("Can't cast shock from hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast " + shock, false);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, true);
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fugitiveWizard);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, fugitiveWizard, 1);
    }
}