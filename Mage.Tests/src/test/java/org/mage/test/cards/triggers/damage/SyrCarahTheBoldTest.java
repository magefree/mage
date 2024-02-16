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
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);
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
    }

    @Test
    public void test_DamageWithCopyAbility() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromHand(playerA);

        // When Syr Carah, the Bold or an instant or sorcery spell you control deals damage to a player, exile the top card of your library. You may play that card this turn.
        // {T}: Syr Carah deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Carah, the Bold", 1);
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 2);
        //
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
        // Equip 3
        addCard(Zone.BATTLEFIELD, playerA, "Illusionist's Bracers", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // prepare equip
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Syr Carah, the Bold");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);

        // activate damage - 2x damage with copy
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);
        setChoice(playerA, false); // no new target for copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("damage 2", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 1 - 1);
        checkExileCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
