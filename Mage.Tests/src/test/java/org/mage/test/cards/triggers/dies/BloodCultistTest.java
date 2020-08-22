
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodCultistTest extends CardTestPlayerBase {

    @Test
    public void testDiedFromDirectDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // {T}: Blood Cultist deals 1 damage to target creature.
        // Whenever a creature dealt damage by Blood Cultist this turn dies, put a +1/+1 counter on Blood Cultist.
        addCard(Zone.HAND, playerA, "Blood Cultist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Devilthorn Fox", 1); // 3/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Cultist");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Devilthorn Fox");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Devilthorn Fox", 1);
        assertPowerToughness(playerA, "Blood Cultist", 2, 2);
    }

    @Test
    public void testDiedFromCombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // {T}: Blood Cultist deals 1 damage to target creature.
        // Whenever a creature dealt damage by Blood Cultist this turn dies, put a +1/+1 counter on Blood Cultist.
        addCard(Zone.HAND, playerA, "Blood Cultist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Devilthorn Fox", 1); // 3/1
        // Lifelink
        // {2}, Sacrifice a creature: Put a +1/+1 counter on each Vampire you control.
        addCard(Zone.BATTLEFIELD, playerB, "Indulgent Aristocrat", 1); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Cultist");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Devilthorn Fox");

        attack(5, playerA, "Blood Cultist");
        block(5, playerB, "Indulgent Aristocrat", "Blood Cultist");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Devilthorn Fox", 1);
        assertGraveyardCount(playerB, "Indulgent Aristocrat", 1);
        assertPowerToughness(playerA, "Blood Cultist", 3, 3);
    }

    @Test
    public void testDiedFromCombatAfterDirectDamageDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {T}: Blood Cultist deals 1 damage to target creature.
        // Whenever a creature dealt damage by Blood Cultist this turn dies, put a +1/+1 counter on Blood Cultist.
        addCard(Zone.HAND, playerA, "Blood Cultist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Devilthorn Fox", 1); // 3/1
        // Shambling Ghoul enters the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerB, "Shambling Ghoul", 1); // 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Cultist");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Devilthorn Fox");
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Shambling Ghoul");
        attack(5, playerA, "Silvercoat Lion");
        block(5, playerB, "Shambling Ghoul", "Silvercoat Lion");

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Devilthorn Fox", 1);
        assertGraveyardCount(playerB, "Shambling Ghoul", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Blood Cultist", 3, 3);
    }

    @Test
    public void testDiedByDirectDamageAfterCombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {T}: Blood Cultist deals 1 damage to target creature.
        // Whenever a creature dealt damage by Blood Cultist this turn dies, put a +1/+1 counter on Blood Cultist.
        addCard(Zone.HAND, playerA, "Blood Cultist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Devilthorn Fox", 1); // 3/1
        // Shambling Ghoul enters the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerB, "Shambling Ghoul", 1); // 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Cultist");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Devilthorn Fox");
        activateAbility(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {this} deals", "Shambling Ghoul");
        attack(5, playerA, "Silvercoat Lion");
        block(5, playerB, "Shambling Ghoul", "Silvercoat Lion");

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Devilthorn Fox", 1);
        assertGraveyardCount(playerB, "Shambling Ghoul", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Blood Cultist", 3, 3);
    }

    @Test
    public void testDiedByTwoDirectDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // {T}: Blood Cultist deals 1 damage to target creature.
        // Whenever a creature dealt damage by Blood Cultist this turn dies, put a +1/+1 counter on Blood Cultist.
        addCard(Zone.HAND, playerA, "Blood Cultist", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Shambling Ghoul", 1); // 2/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blood Cultist");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", "Shambling Ghoul");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Shambling Ghoul");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Shambling Ghoul", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPowerToughness(playerA, "Blood Cultist", 2, 2);
    }
}
