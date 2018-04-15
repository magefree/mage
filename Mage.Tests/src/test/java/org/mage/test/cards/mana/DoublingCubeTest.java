package org.mage.test.cards.mana;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DoublingCubeTest extends CardTestPlayerBase {

    // {3}, {T}: Double the amount of each type of mana in your mana pool.
    String cube = "Doubling Cube";
    // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
    String temple = "Eldrazi Temple";
    // Mana pools don't empty as steps and phases end.
    String upwelling = "Upwelling";

    //issue 3443
    @Test
    public void DoublingCubeEldraziTemple() {

        addCard(Zone.BATTLEFIELD, playerA, temple);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, cube);
        addCard(Zone.BATTLEFIELD, playerA, upwelling);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}:");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertManaPool(playerA, ManaType.COLORLESS, 4);

    }
}
