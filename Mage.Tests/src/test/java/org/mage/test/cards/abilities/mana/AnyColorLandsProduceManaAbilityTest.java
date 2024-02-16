package org.mage.test.cards.abilities.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * These tests check that {@link mage.abilities.mana.AnyColorLandsProduceManaAbility AnyColorLandsProduceManaAbility}
 * works properly abilities where Colorless isn't a valid option.
 * <p>
 * The test for ensuring that it works with colorless mana is handled in {@link org.mage.test.cards.mana.ReflectingPoolTest}.
 *
 * @author Alex-Vasile
 */
public class AnyColorLandsProduceManaAbilityTest extends CardTestPlayerBase {

    // Any color (but not colorless since that's not a color)
    private static final String fellwarStone = "Fellwar Stone";

    /**
     * Fellwar Stone should not be able to tap for {C}.
     * <p>
     * {@link mage.cards.f.FellwarStone Fellwar Stone}
     * {T}: Add one mana of any color that a land an opponent controls could produce.
     */
    @Test
    public void testColorlessNotAllowed() {
        addCard(Zone.BATTLEFIELD, playerA, fellwarStone);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Desert");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{R}", manaOptions);
    }
}
