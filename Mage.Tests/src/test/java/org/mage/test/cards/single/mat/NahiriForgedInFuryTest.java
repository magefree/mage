package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 * @author correl
 */
public class NahiriForgedInFuryTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.NahiriForgedInFury} {4}{R}{W}
     * Legendary Creature - Kor Artificer
     *
     * Affinity for Equipment
     *
     * Whenever an equipped creature you control attacks, exile the top card of
     * your library. You may play that card this turn. You may cast Equipment
     * spells this way without paying their mana costs.
     */
    private static final String nahiri = "Nahiri, Forged in Fury";

    private final String boots = "Swiftfoot Boots";
    private final String cleaver = "The Reaver Cleaver";
    private final String giant = "Hill Giant";
    private final String greaves = "Lightning Greaves";
    private final String lions = "Savannah Lions";
    private final String sword = "Sword of Feast and Famine";

    @Test
    public void test_CostReducedByEquipment() {
        // Nahiri in hand, four equipment in play, and enough to pay RW
        addCard(Zone.HAND, playerA, nahiri);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, boots);
        addCard(Zone.BATTLEFIELD, playerA, cleaver);
        addCard(Zone.BATTLEFIELD, playerA, greaves);
        addCard(Zone.BATTLEFIELD, playerA, sword);

        // Cast for RW (Reduced by 4)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nahiri);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_EquippedAttackTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, nahiri);
        addCard(Zone.BATTLEFIELD, playerA, lions);
        addCard(Zone.BATTLEFIELD, playerA, giant);
        addCard(Zone.BATTLEFIELD, playerA, boots);
        addCard(Zone.BATTLEFIELD, playerA, greaves);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", lions);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", giant);

        // Attack with three creatures, two of which are equipped
        attack(1, playerA, nahiri);
        attack(1, playerA, lions);
        attack(1, playerA, giant);

        // Order the 2 Nahiri triggers
        setChoice(playerA, "Whenever an equipped creature you control attacks");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Triggered twice, exiling two cards
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_CanCastExiledEquipmentForFree() {
        addCard(Zone.BATTLEFIELD, playerA, nahiri);
        addCard(Zone.BATTLEFIELD, playerA, greaves);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, sword);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", nahiri);

        // Attack with one equipped creature, exiling the sword
        attack(1, playerA, nahiri);

        // Cast sword for free
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sword);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
