package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 *
 * @author LevelX2
 */
public class TappedForManaRelatedTest extends CardTestPlayerBase {

    /**
     * This is a new rule that slightly changes how we resolve abilities that
     * trigger whenever a permanent is tapped for mana or for mana of a
     * specified type. Now, you look at what was actually produced after the
     * activated mana ability resolves. So, tapping a Gaea's Cradle while you no
     * control no creatures won't cause a Wild Growth attached to it to trigger.
     */
    @Test
    public void TestCradleWithWildGrowthNoCreatures() {
        // {T}: Add {G} for each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Gaea's Cradle");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}", manaOptions);

    }

    @Test
    public void TestCradleWithWildGrowthTwoCreatures() {
        // {T}: Add {G} for each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Gaea's Cradle");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}{G}{G}", manaOptions);

    }

    @Test
    public void TestWildGrowth() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Enchant land
        // Whenever enchanted land is tapped for mana, its controller adds {G}.
        addCard(Zone.HAND, playerA, "Wild Growth", 1); // Enchantment {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Growth", "Forest");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Wild Growth", 1);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}", manaOptions);

    }

}
