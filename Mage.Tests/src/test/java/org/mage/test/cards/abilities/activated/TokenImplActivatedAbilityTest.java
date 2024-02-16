
package org.mage.test.cards.abilities.activated;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TokenImplActivatedAbilityTest extends CardTestPlayerBase {

    /**
     * Check that activated ability of created token works
     */
    @Test
    public void testActivatedManaAbility() {
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Freyalise, Llanowar's Fury");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Create a 1/1 green Elf Druid creature token with \"{T}: Add {G}.\"");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Create a 1/1 green Elf Druid creature token with \"{T}: Add {G}.\"");
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}.");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elf Druid Token", 2);
        assertPermanentCount(playerA, "Freyalise, Llanowar's Fury", 1);
        Assert.assertEquals("one green mana has to be in the mana pool", 1, playerA.getManaPool().get(ManaType.GREEN));
    }
}
