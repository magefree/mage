package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SyrCarahTheBoldTest extends CardTestPlayerBase {

    @Test
    public void test_Damage() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromHand(playerA);
        // When Syr Carah, the Bold or an instant or sorcery spell you control deals damage to a player, exile the top card of your library. You may play that card this turn.
        // {T}: Syr Carah deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Carah, the Bold", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 5);
        //
        // {1}, {T}, Sacrifice Aeolipile: It deals 2 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Aeolipile", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        // 1 - triggers on ability damage
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("damage 1", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 1);

        // 2 - triggers on spell damage
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkLife("damage 2", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 1 - 3);

        // 3 - NONE triggers on another ability damage
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, {T}, Sacrifice", playerA);
        checkLife("damage 3", 2, PhaseStep.BEGIN_COMBAT, playerA, 20 - 2);

        // 4 - triggers on combat damage
        attack(3, playerA, "Syr Carah, the Bold", playerB);
        checkLife("damage 4", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, 20 - 1 - 3 - 3);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_DamageWithCopyAbility() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromHand(playerA);
        // When Syr Carah, the Bold or an instant or sorcery spell you control deals damage to a player, exile the top card of your library. You may play that card this turn.
        // {T}: Syr Carah deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Carah, the Bold", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Swamp", 5);
        //
        // {T}: Embermage Goblin deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Embermage Goblin", 1);
        //
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
        // Equip 3
        addCard(Zone.BATTLEFIELD, playerB, "Illusionist's Bracers", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);

        // equip to copy abilities
        showAvaileableAbilities("abils", 2, PhaseStep.PRECOMBAT_MAIN, playerB);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {3}", "Embermage Goblin");
        setChoice(playerB, "No"); // no new target

        // 3 - 2x damage (copy), but no trigger
        // java.lang.ClassCastException: mage.game.stack.StackAbility cannot be cast to mage.game.stack.Spell
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: {source} deals", playerA);
        checkLife("damage 3", 2, PhaseStep.END_TURN, playerA, 20 - 1 - 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
