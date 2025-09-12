package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class CarnageCrimsonChaosTest extends CardTestPlayerBase {

    /*
    Carnage, Crimson Chaos
    {2}{B}{R}
    Legendary Creature - Symbiote Villain
    Trample
    When Carnage enters, return target creature card with mana value 3 or less from your graveyard to the battlefield. It gains "This creature attacks each combat if able" and "When this creature deals combat damage to a player, sacrifice it."
    Mayhem {B}{R}
    4/3
    */
    private static final String carnageCrimsonChaos = "Carnage, Crimson Chaos";

    /*
    Concordant Crossroads
    {G}
    World Enchantment
    All creatures have haste.
    */
    private static final String concordantCrossroads = "Concordant Crossroads";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testCarnageCrimsonChaos() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, concordantCrossroads);
        addCard(Zone.HAND, playerA, carnageCrimsonChaos);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, carnageCrimsonChaos);
        addTarget(playerA, bearCub);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, bearCub, 1);
        assertLife(playerB, 20 - 2);
    }
}