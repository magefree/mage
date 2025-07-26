package org.mage.test.cards.triggers;

import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EnduringGlimmerTriggeredAbilityTest extends CardTestPlayerBase {
    // Enchantment Creature - Dog Glimmer - 3/3
    // Whenever another creature you control enters, it gets +2/+0 and gains haste until end of turn.
    // When Enduring Courage dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
    static String courage = "Enduring Courage";
    // Deal 3 damage to any target
    static String bolt = "Lightning Bolt";
    // Creature - Bear - 2/2
    static String cub = "Bear Cub";

    @Test
    public void testEnduringCourage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, courage);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cub);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkAbility("Bear Cub has haste", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cub, HasteAbility.class, true);
        checkPT("Bear Cub has +2/+0", 1, PhaseStep.PRECOMBAT_MAIN, playerA, cub, 2 + 2, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, courage);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkType("Courage is just an enchantment",1, PhaseStep.PRECOMBAT_MAIN, playerA, courage, CardType.CREATURE, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
