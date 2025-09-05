package org.mage.test.cards.single.m19;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlpineMoonTest extends CardTestPlayerBase {
    /*
    Alpine Moon
    {R}
    Enchantment
    As this enchantment enters, choose a nonbasic land card name.
    Lands your opponents control with the chosen name lose all land types and abilities, and they gain “{T}: Add one mana of any color.”
     */
    private static final String alpine = "Alpine Moon";
    /*
    Urborg, Tomb of Yawgmoth
    Legendary Land
    Each land is a Swamp in addition to its other land types.
     */
    private static final String urborg = "Urborg, Tomb of Yawgmoth";

    @Test
    public void testAlpineMoonAfterUrborg() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, urborg);
        addCard(Zone.BATTLEFIELD, playerB, alpine);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        setChoice(playerB, urborg);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertNotSubtype(urborg, SubType.SWAMP);
        assertSubtype("Mountain", SubType.SWAMP);
        assertSubtype("Island", SubType.SWAMP);
        assertAbility(playerA, urborg, new AnyColorManaAbility(), true);
    }

    @Test
    public void testAlpineMoonBeforeUrborg() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerB, urborg);
        addCard(Zone.BATTLEFIELD, playerA, alpine);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        setChoice(playerA, urborg);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertNotSubtype(urborg, SubType.SWAMP);
        assertSubtype("Mountain", SubType.SWAMP);
        assertSubtype("Island", SubType.SWAMP);
        assertAbility(playerB, urborg, new AnyColorManaAbility(), true);
    }
}
