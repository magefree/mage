
package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ShamenEnKorTest extends CardTestPlayerBase {

    /**
     * Tests that 2 of 3 damage is redirected while 1 damage is still dealt to
     * original target
     */
    @Test
    public void testFirstAbilityNonCombatDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        addCard(Zone.BATTLEFIELD, playerB, "Shaman en-Kor"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Shaman en-Kor");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: The next 1 damage", "Silvercoat Lion", "Lightning Bolt");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{0}: The next 1 damage", "Silvercoat Lion", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Shaman en-Kor", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testSecondAbilityNonCombatDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // {0}: The next 1 damage that would be dealt to Shaman en-Kor this turn is dealt to target creature you control instead.
        // {1}{W}: The next time a source of your choice would deal damage to target creature this turn, that damage is dealt to Shaman en-Kor instead.
        addCard(Zone.BATTLEFIELD, playerB, "Shaman en-Kor"); // 1/2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{W}: The next time", "Silvercoat Lion", "Lightning Bolt");
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Shaman en-Kor", 1);

    }

}
