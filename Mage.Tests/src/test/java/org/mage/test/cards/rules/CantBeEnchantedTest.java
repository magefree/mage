
package org.mage.test.cards.rules;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantBeEnchantedTest extends CardTestPlayerBase {

    @Test
    public void testConsecrateLand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Enchant land
        // Enchanted land is indestructible and can't be enchanted by other Auras.
        addCard(Zone.HAND, playerA, "Consecrate Land", 1); // Enchantment {W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant land
        // Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.
        addCard(Zone.HAND, playerB, "Psychic Venom", 1); // Enchantment {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consecrate Land", "Plains");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Psychic Venom", "Plains");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Consecrate Land", 0);
        assertPermanentCount(playerA, "Consecrate Land", 1);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true);
        assertPermanentCount(playerB, "Psychic Venom", 0);
        assertGraveyardCount(playerB, "Psychic Venom", 1);

    }

    @Test
    public void testConsecrateLandEnchantedBefore() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Enchant land
        // Enchanted land is indestructible and can't be enchanted by other Auras.
        addCard(Zone.HAND, playerA, "Consecrate Land", 1); // Enchantment {W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Enchant land
        // Whenever enchanted land becomes tapped, Psychic Venom deals 2 damage to that land's controller.
        addCard(Zone.HAND, playerB, "Psychic Venom", 1); // Enchantment {1}{U}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Psychic Venom", "Plains");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Consecrate Land", "Plains");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Consecrate Land", 0);
        assertPermanentCount(playerA, "Consecrate Land", 1);
        assertAbility(playerA, "Plains", IndestructibleAbility.getInstance(), true);

        assertLife(playerA, 18);
        assertPermanentCount(playerB, "Psychic Venom", 0);
        assertGraveyardCount(playerB, "Psychic Venom", 1);
        assertHandCount(playerB, "Psychic Venom", 0);

    }
}
