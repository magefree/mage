package org.mage.test.cards.single.lea;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.g.GaeasLiege Gaea's Liege} {3}{G}{G}{G}, 0/0
 * As long as Gaea's Liege isn't attacking, its power and toughness are each equal to the number of Forests you
 * control. As long as Gaea's Liege is attacking, its power and toughness are each equal to the number of Forests
 * defending player controls.
 * {T}: Target land becomes a Forest until Gaea's Liege leaves the battlefield.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class GaeasLiegeTest extends CardTestPlayerBase {

    private static final String liege = "Gaea's Liege";

    /**
     * A token copy's land stops being a Forest the moment the token leaves, not once the stack is empty.
     */
    @Test
    public void testTokenCopyLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest"); // keeps the 0/0 Lieges alive
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, liege);
        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U} instant: token copy of target creature you control
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", liege, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", liege + "[no copy]", true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target land", "Plains");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkSubType("the token turns it", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains", SubType.FOREST, true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", liege, "Shock");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        checkSubType("token gone, Shock still on the stack", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains", SubType.FOREST, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, liege, 0);
        assertLife(playerB, 20 - 2);
    }
}
