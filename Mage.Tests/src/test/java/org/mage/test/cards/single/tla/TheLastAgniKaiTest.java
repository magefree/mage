package org.mage.test.cards.single.tla;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TheLastAgniKaiTest extends CardTestPlayerBase {

    @Test
    public void test_ExcessDamage_AddsRedMana() {
        // The Last Agni Kai ruling:
        // Excess damage has been dealt to a creature if the damage dealt to it is greater than lethal damage.
        // Usually, this means damage greater than its toughness, although damage already marked on the
        // creature is taken into account.
        // (2025-10-02)
        // Target creature you control fights target creature an opponent controls.
        // If the creature the opponent controls is dealt excess damage this way, add that much {R}.
        // Until end of turn, you don't lose unspent red mana as steps and phases end.
        addCard(Zone.HAND, playerA, "The Last Agni Kai"); // {1}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Baneslayer Angel"); // 5/5
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Last Agni Kai", "Baneslayer Angel^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 5);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertManaPool(playerA, ManaType.RED, 3);
    }

    @Test
    public void test_AllDamagePrevented_NoRedMana() {
        // The Last Agni Kai ruling:
        // Excess damage has been dealt to a creature if the damage dealt to it is greater than lethal damage.
        // Usually, this means damage greater than its toughness, although damage already marked on the
        // creature is taken into account.
        // (2025-10-02)
        //
        // Target creature you control fights target creature an opponent controls.
        // If the creature the opponent controls is dealt excess damage this way, add that much {R}.
        // Until end of turn, you don't lose unspent red mana as steps and phases end.
        addCard(Zone.HAND, playerA, "The Last Agni Kai"); // {1}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Baneslayer Angel"); // 5/5
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        // Prevent all noncombat damage that would be dealt to creatures you control.
        addCard(Zone.BATTLEFIELD, playerB, "Mark of Asylum");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Last Agni Kai", "Baneslayer Angel^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertManaPool(playerA, ManaType.RED, 0);
    }

    @Test
    public void test_OneDamagePrevented_AddsRedManaForRemainingExcess() {
        // The Last Agni Kai ruling:
        // Excess damage has been dealt to a creature if the damage dealt to it is greater than lethal damage.
        // Usually, this means damage greater than its toughness, although damage already marked on the
        // creature is taken into account.
        // (2025-10-02)
        //
        // Target creature you control fights target creature an opponent controls.
        // If the creature the opponent controls is dealt excess damage this way, add that much {R}.
        // Until end of turn, you don't lose unspent red mana as steps and phases end.
        addCard(Zone.HAND, playerA, "The Last Agni Kai"); // {1}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Baneslayer Angel"); // 5/5
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        // Prevent the next 1 damage that would be dealt to any target this turn.
        addCard(Zone.HAND, playerB, "Bandage"); // {W}
        // {T}: Add {W}.
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Bandage", "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Last Agni Kai", "Baneslayer Angel^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 4);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertManaPool(playerA, ManaType.RED, 2);
    }

    @Test
    public void test_DoubledDamage_AddsRedManaForAllExcess() {
        // The Last Agni Kai ruling:
        // Excess damage has been dealt to a creature if the damage dealt to it is greater than lethal damage.
        // Usually, this means damage greater than its toughness, although damage already marked on the
        // creature is taken into account.
        // (2025-10-02)
        //
        // Target creature you control fights target creature an opponent controls.
        // If the creature the opponent controls is dealt excess damage this way, add that much {R}.
        // Until end of turn, you don't lose unspent red mana as steps and phases end.
        addCard(Zone.HAND, playerA, "The Last Agni Kai"); // {1}{R}
        // {T}: Add {R}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Baneslayer Angel"); // 5/5
        // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        // If a source you control would deal damage to a permanent or player,
        // it deals double that damage to that permanent or player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Angrath's Marauders");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Last Agni Kai", "Baneslayer Angel^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 10);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertManaPool(playerA, ManaType.RED, 8);
    }
}
