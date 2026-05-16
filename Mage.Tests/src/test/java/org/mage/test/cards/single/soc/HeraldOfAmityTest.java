package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class HeraldOfAmityTest extends CardTestPlayerBase {

    @Test
    public void testCastsExiledAuraForFreeAndCountsAuras() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Herald of Amity");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerA, "Ethereal Armor");
        addCard(Zone.LIBRARY, playerA, "Island", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Herald of Amity");
        setChoice(playerA, "Ethereal Armor");
        setChoice(playerA, true);
        addTarget(playerA, "Herald of Amity");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ethereal Armor", 1);
        assertPowerToughness(playerA, "Herald of Amity", 3, 3);
    }
}
