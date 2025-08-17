package org.mage.test.cards.single.c13;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ActOfAuthorityEffectTest extends CardTestPlayerBase {
    /*
    Act of Authority
    {1}{W}{W}
    Enchantment
    When this enchantment enters, you may exile target artifact or enchantment.
    At the beginning of your upkeep, you may exile target artifact or enchantment. If you do, its controller gains control of this enchantment.
     */
    private static final String actOfAuthority = "Act of Authority";

    @Test
    public void testActOfAuthority() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, actOfAuthority);
        addCard(Zone.BATTLEFIELD, playerB, actOfAuthority + "@actB");

        setChoice(playerA, true); // upkeep
        addTarget(playerA, "@actB");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, actOfAuthority, 1);
    }

}
