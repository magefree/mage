package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx, Loki
 */
public class BloodbraidElfTest extends CardTestPlayerBase {
    /*
     *  Terminus
     *      Sorcery, 4WW (6)
     *      Put all creatures on the bottom of their owners' libraries.
     *      Miracle {W} (You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)
     *
     *  Bloodbraid Elf
     *      Creature — Elf Berserker 3/2, 2RG (4)
     *      Haste
     *      Cascade (When you cast this spell, exile cards from the top of your library until you exile a nonland card that costs
     *      less. You may cast it without paying its mana cost. Put the exiled cards on the bottom in a random order.)
     *
     *  Goblin Wardriver
     *      Creature — Goblin Warrior 2/2, RR (2)
     *      Battle cry (Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.)
     *
     */

    @Test
    public void testCascade() {
        addCard(Zone.HAND, playerA, "Bloodbraid Elf");
        addCard(Zone.HAND, playerA, "Terminus");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Wardriver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminus");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bloodbraid Elf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Bloodbraid Elf", 1);
        assertPermanentCount(playerA, "Goblin Wardriver", 1);
    }
}
