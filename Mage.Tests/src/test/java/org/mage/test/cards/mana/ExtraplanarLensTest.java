package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExtraplanarLensTest extends CardTestPlayerBase {

    // Imprint - When Extraplanar Lens enters the battlefield, you may exile target land you control.
    // Whenever a land with the same name as the exiled card is tapped for mana, its controller adds one mana of any type that land produced.
    private final String lens = "Extraplanar Lens";
    //
    private final String snow_covered_forest = "Snow-Covered Forest";
    private final String forest = "Forest";


    // issue 8050
    // Extraplanar Lens is not working when I exiled a snow-covered land

    @Test
    public void add_snow_mana() {
        addCard(Zone.HAND, playerA, lens);
        addCard(Zone.BATTLEFIELD, playerA, snow_covered_forest, 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lens);
        setChoice(playerA, true);
        addTarget(playerA, snow_covered_forest);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, lens, 1);
        assertExileCount(snow_covered_forest, 1);


    }

    @Test
    public void add_regular_mana() {
        addCard(Zone.HAND, playerA, lens);
        addCard(Zone.BATTLEFIELD, playerA, forest, 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lens);
        setChoice(playerA, true);
        addTarget(playerA, forest);

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkManaPool("mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 2);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertPermanentCount(playerA, lens, 1);
        assertExileCount(forest, 1);


    }
}
