
package org.mage.test.cards.single.mrd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SoulFoundryTest extends CardTestPlayerBase {

    /**
     * Soul Foundry imprinted with Bloodline Keeper costs 8 colorless mana to
     * use the ability instead of 4 (the converted mana cost of Bloodline
     * Keeper).
     */
    @Test
    public void testBloodlineKeeper() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);
        // Imprint - When Soul Foundry enters the battlefield, you may exile a creature card from your hand.
        // {X}, {T}: Create a tokenthat's a copy of the exiled card onto the battlefield. X is the converted mana cost of that card.
        addCard(Zone.HAND, playerA, "Soul Foundry"); // {4}
        addCard(Zone.HAND, playerA, "Bloodline Keeper");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Foundry");
        setChoice(playerA, true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}, {T}: Create a token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soul Foundry", 1);

        assertExileCount("Bloodline Keeper", 1);
        assertPermanentCount(playerA, "Bloodline Keeper", 1);
    }
}
