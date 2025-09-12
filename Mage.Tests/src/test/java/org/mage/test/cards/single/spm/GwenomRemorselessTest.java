package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class GwenomRemorselessTest extends CardTestPlayerBase {

    /*
    Gwenom, Remorseless
    {3}{B}{B}
    Legendary Creature - Symbiote Spider Hero
    Deathtouch, lifelink
    Whenever Gwenom attacks, until end of turn you may look at the top card of your library any time and you may play cards from the top of your library. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
    4/4
    */
    private static final String gwenomRemorseless = "Gwenom, Remorseless";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testGwenomRemorseless() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gwenomRemorseless);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, bearCub, 2);
        addCard(Zone.LIBRARY, playerA, "Forest");

        attack(1, playerA, gwenomRemorseless);
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bearCub);

        // effect ends at end of turn
        checkPlayableAbility("Can't play top card", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Island", false);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bearCub, 1);
        assertPermanentCount(playerA, gwenomRemorseless, 1);
        assertPermanentCount(playerA, "Forest", 1);
        assertLife(playerB, 20 - 4);
        assertLife(playerA, 20 + 4 - 2);
    }
}