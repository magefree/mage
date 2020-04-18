package org.mage.test.cards.single.c20;

import mage.abilities.keyword.ProtectionAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterCard;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SanctuaryBladeTest extends CardTestPlayerBase {

    @Test
    public void testEquipped() {
        addCard(Zone.BATTLEFIELD, playerA, "Sanctuary Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Savai Sabertooth");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Savai Sabertooth");
        setChoice(playerA, "Black");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPowerToughness(playerA, "Savai Sabertooth", 5, 1);
        assertAbility(playerA, "Savai Sabertooth", new ProtectionAbility(new FilterCard("black")), true);
    }

}
