package org.mage.test.cards.single.m21;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChandrasPyrelingTest extends CardTestPlayerBase {

    @Test
    public void boostAndGetDoubleStrike(){
        // Whenever a source you control deals noncombat damage to an opponent,
        // Chandra's Pyreling gets +1/+0 and gains double strike until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Pyreling");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertAbility(playerA, "Chandra's Pyreling", DoubleStrikeAbility.getInstance(), true);
        assertPowerToughness(playerA, "Chandra's Pyreling", 2, 3);

    }
}
