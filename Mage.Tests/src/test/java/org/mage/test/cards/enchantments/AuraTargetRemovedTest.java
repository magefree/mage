package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class AuraTargetRemovedTest extends CardTestPlayerBase {

    /**
     * Spreading Seas is bugged, opp casted it on my Field of Ruin, I sacced
     * with the spell on stack but it resolved anyway and let them draw.
     *
     * 303.4. Some enchantments have the subtype “Aura.” An Aura enters the
     * battlefield attached to an object or player. What an Aura can be attached
     * to is restricted by its enchant keyword ability (see rule 702.5,
     * “Enchant”). Other effects can limit what a permanent can be enchanted by.
     * 303.4a An Aura spell requires a target, which is restricted by its
     * enchant ability.
     */
    @Test
    public void testOneAttackerDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Academy Ruins", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); //Enchantment {1}{U}

        // {T}: Add {C}.
        // {2}, {T}, Sacrifice Field of Ruin: Destroy target nonbasic land an opponent controls. Each player searches their library for a basic land card, puts it onto the battlefield, then shuffles their library.
        addCard(Zone.BATTLEFIELD, playerB, "Field of Ruin", 1); //
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Field of Ruin");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, {T}", "Academy Ruins", "Spreading Seas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, 3);
        assertGraveyardCount(playerB, "Field of Ruin", 1);

        assertPermanentCount(playerA, 3);
        assertGraveyardCount(playerA, "Spreading Seas", 1);
        assertGraveyardCount(playerA, "Academy Ruins", 1);

        assertHandCount(playerA, 0); // Because Spreading Seas is counterd (no valid target), no card is drawn from it
    }

}
