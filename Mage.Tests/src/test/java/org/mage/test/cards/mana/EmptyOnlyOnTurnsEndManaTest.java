
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EmptyOnlyOnTurnsEndManaTest extends CardTestPlayerBase {

    @Test
    public void testDaxosOfMeletis() {
        // At the beginning of each player's upkeep, that player adds {G}{G}{G}. Until end of turn, this mana doesn't empty from that player's mana pool as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Shizuko, Caller of Autumn", 1);
        addCard(Zone.HAND, playerA, "Birds of Paradise", 1);

        addCard(Zone.HAND, playerB, "Birds of Paradise", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birds of Paradise");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Birds of Paradise");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Birds of Paradise", 1);
        assertPermanentCount(playerB, "Birds of Paradise", 1);

        Assert.assertEquals("2 {G} have to be still im Mana Pool", "{G}{G}", playerB.getManaPool().getMana().toString());

    }

}
