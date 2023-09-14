package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LiberatedLivestockTest extends CardTestPlayerBase {
    /*
    testlist
    3 tokens enters after death
    6 tokens with doubling season
    auras from hand
    auras from graveyard
    auras from hand and graveyard
    auras from hand and graveyard & doubleing season
    no errors with bad mama norn
     */
    
    /**
     * Liberated Livestock
     * {5}{W}
     * Sorcery
     *
     * When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink,
     * a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token.
     * For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.
     * */
    private static final String LIBERATEDLIVESTOCK = "Liberated Livestock";
    private static final String MURDER = "Murder";


    @Test
    public void threeTokensAfterDeath() {
        addCard(Zone.HAND, playerA, MURDER);
        addCard(Zone.BATTLEFIELD, playerA, LIBERATEDLIVESTOCK);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, MURDER, LIBERATEDLIVESTOCK);
        //addTarget(playerA, TestPlayer.TARGET_SKIP);
        //addTarget(playerA, TestPlayer.TARGET_SKIP);
        //addTarget(playerA, TestPlayer.TARGET_SKIP);

        waitStackResolved(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Cat Token",1);
        assertPermanentCount(playerA, "Bird Token",1);
        assertPermanentCount(playerA, "Ox Token",1);
    }

}


