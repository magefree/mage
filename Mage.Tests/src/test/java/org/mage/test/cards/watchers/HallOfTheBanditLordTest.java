package org.mage.test.cards.watchers;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HallOfTheBanditLordTest extends CardTestPlayerBase {
    /*
     * Hall of the Bandit Lord
     * Legendary Land
     * Hall of the Bandit Lord enters the battlefield tapped.
     * {T}, Pay 3 life: Add {C}. If that mana is spent on a
     * creature spell, it gains haste.
     *
     */

    // test that a creature cast using Hall of the Bandit Lord mana gains haste
    @Test
    public void testGainsHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Hall of the Bandit Lord");
        addCard(Zone.HAND, playerA, "Goblin Roughrider"); // Creature 3/3 - {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        this.assertAbility(playerA, "Goblin Roughrider", HasteAbility.getInstance(), true);
    }

    // test that a creature cast not using Hall of the Bandit Lord mana does not gain haste
    @Test
    public void testNotGainsHaste() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Hall of the Bandit Lord");
        addCard(Zone.HAND, playerA, "Ember Hauler");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ember Hauler");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        this.assertAbility(playerA, "Ember Hauler", HasteAbility.getInstance(), false);
    }
}
