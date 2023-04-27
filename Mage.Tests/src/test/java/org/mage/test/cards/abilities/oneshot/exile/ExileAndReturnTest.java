package org.mage.test.cards.abilities.oneshot.exile;

import mage.abilities.keyword.ReachAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ExileAndReturnTest extends CardTestPlayerBase {

    @Test
    public void testExileAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}", "Silvercoat Lion");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testExileAndReturnWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        // Put a +1/+1 counter on target creature.
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.HAND, playerB, "Battlegrowth"); // Instant {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", "Silvercoat Lion");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}", "Silvercoat Lion");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);
        assertGraveyardCount(playerB, "Battlegrowth", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 3, 3);
    }

    @Test
    public void testExileAndReturnWithCountersAndAuras() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        // Whenever an Aura becomes attached to Bramble Elemental, put two 1/1 green Saproling creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Bramble Elemental"); // Creature 4/4

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 5);
        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerB, "Battlegrowth"); // Instant {G}
        // Enchant creature (Target a creature as you cast this. This card enters the battlefield attached to that creature.)
        // Enchanted creature gets +1/+1 for each Forest you control.
        addCard(Zone.HAND, playerB, "Blanchwood Armor"); // Enchantment Aura {2}{G}
        // Enchant creature
        // When Frog Tongue enters the battlefield, draw a card.
        // Enchanted creature has reach. (It can block creatures with flying.)
        addCard(Zone.HAND, playerB, "Frog Tongue"); // Enchantment Aura {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin", true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", "Bramble Elemental");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Blanchwood Armor", "Bramble Elemental");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Frog Tongue", "Bramble Elemental");
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "{3}, {T}", "Bramble Elemental");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Tawnos's Coffin", 1);
        assertTapped("Tawnos's Coffin", false);
        assertGraveyardCount(playerB, "Battlegrowth", 1);
        assertPermanentCount(playerB, "Bramble Elemental", 1);
        assertGraveyardCount(playerB, "Blanchwood Armor", 0);
        assertPermanentCount(playerB, "Blanchwood Armor", 1);
        assertGraveyardCount(playerB, "Frog Tongue", 0);
        assertPermanentCount(playerB, "Frog Tongue", 1);
        assertPermanentCount(playerB, "Saproling Token", 8);
        assertPowerToughness(playerB, "Bramble Elemental", 10, 10);
        assertAbility(playerB, "Bramble Elemental", ReachAbility.getInstance(), true);

        assertHandCount(playerB, 3); // 1 from Turn 2 and 2 from Frog Tongue
    }

    @Test
    public void testExileAndReturnIfTawnosLeftBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        // You may choose not to untap Tawnos's Coffin during your untap step.
        // {3}, {T}: Exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        // When Tawnos's Coffin leaves the battlefield or becomes untapped, return the exiled card to the battlefield under
        //   its owner's control tapped with the noted number and kind of counters on it, and if you do, return the exiled Aura
        //   cards to the battlefield under their owner's control attached to that permanent.
        addCard(Zone.HAND, playerA, "Tawnos's Coffin"); // Artifact {4}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Disenchant");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tawnos's Coffin", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Tawnos's Coffin");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Disenchant", 1);

        assertPermanentCount(playerA, "Tawnos's Coffin", 0);
        assertGraveyardCount(playerA, "Tawnos's Coffin", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }
}
