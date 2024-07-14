package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class GiftTest extends CardTestPlayerBase {

    private static final String truce = "Dawn's Truce";

    @Test
    public void testNoGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), false, 2);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testGift() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, truce);

        setChoice(playerA, true);
        setChoice(playerA, playerB.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, truce);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Plains", HexproofAbility.getInstance(), true, 2);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true, 2);
        assertHandCount(playerB, 1);
    }
}
