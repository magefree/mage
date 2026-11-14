package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SkullclampTest extends CardTestPlayerBase {

    /**
     * Skullclamp and the creature it's attached to are destroyed by same
     * Pernicious Deed activation. Skullclamp should trigger.
     *
     * 400.7f Abilities that trigger when an enchanted permanent leaves the
     * battlefield can find the new object that each Aura enchanting that
     * permanent became in its owner's graveyard if it was put into that
     * graveyard at the same time the enchanted permanent left the battlefield.
     * It can also find the new object that each Aura enchanting it became in
     * its owner's graveyard as a result of being put there as a state-based
     * action for not being attached to a permanent. (See rule 704.5m.)
     *
     * Skullclamp is destroyed first, so it's no longer attached when the creature's death is checked.
     */
    @Test
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

    /**
     * Austere Command's modes are separate instructions, so Skullclamp has left before the creature dies.
     */
    @Test
    public void testDestroyedBeforeCreature() {
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Skullclamp");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Austere Command"); // {4}{W}{W}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Austere Command");
        setModeChoice(playerA, "1"); // Destroy all artifacts.
        setModeChoice(playerA, "3"); // Destroy all creatures with mana value 3 or less.

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Skullclamp", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, 0);
    }

}
