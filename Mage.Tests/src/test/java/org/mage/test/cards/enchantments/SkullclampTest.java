package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SkullclampTest extends CardTestPlayerBase {

    /**
     * Skullclamp and the creature it's attached to are destroyed by same
     * Pernicious Deed activation. AFAIK Skullclamp should trigger, but it
     * doesn't.
     *
     * 400.7e Abilities of Auras that trigger when the enchanted permanent
     * leaves the battlefield can find the new object that Aura became in its
     * owners graveyard if it was put into that graveyard at the same time the
     * enchanted permanent left the battlefield. It can also find the new object
     * that Aura became in its owners graveyard as a result of being put there
     * as a state-based action for not being attached to a permanent. (See rule
     * 704.5n.)
     *
     */
    @Test
    @Ignore
    public void testPerniciousDeed() {
        // Equipped creature gets +1/-1.
        // Whenever equipped creature dies, draw two cards.
        // Equip {1}
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Skullclamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        // {X}, Sacrifice Pernicious Deed: Destroy each artifact, creature, and enchantment with converted mana cost X or less.
        addCard(Zone.BATTLEFIELD, playerB, "Pernicious Deed"); // Enchantment

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{X}, Sacrifice");
        setChoice(playerB, "X=2");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Skullclamp", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pernicious Deed", 1);

        assertPermanentCount(playerA, "Pillarfield Ox", 1);

        assertHandCount(playerA, 2);
    }

}
