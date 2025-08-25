package org.mage.test.cards.single.one;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class NahirisSacrificeTest extends CardTestPlayerBase {

    /*
    Nahiri's Sacrifice
    {1}{R}
    Sorcery

    As an additional cost to cast this spell, sacrifice an artifact or creature with mana value X.

    Nahiri’s Sacrifice deals X damage divided as you choose among any number of target creatures.
     */
    private static final String nahirisSacrifice = "Nahiri's Sacrifice";
    private static final String balduvianBears = "Balduvian Bears";
    @Test
    public void testNahirisSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, nahirisSacrifice);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, balduvianBears + "@bearsB");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nahirisSacrifice, "@bearsB");
        setChoice(playerA, "X=2");
        setChoice(playerA, balduvianBears);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, balduvianBears, 1);
        assertGraveyardCount(playerA, nahirisSacrifice, 1);
    }

    @Test
    public void testNahirisSacrificePrevented() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, nahirisSacrifice);
        addCard(Zone.BATTLEFIELD, playerA, balduvianBears);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, balduvianBears + "@bearsB");
        addCard(Zone.BATTLEFIELD, playerB, "Yasharn, Implacable Earth");

        checkPlayableAbility("Can't cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + nahirisSacrifice, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
