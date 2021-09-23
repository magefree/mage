
package org.mage.test.cards.mana.conditional;

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
public class TitansNestTest extends CardTestPlayerBase {

    @Test
    public void testTitansNest(){
        setStrictChooseMode(true);
        
        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        // Exile a card from your graveyard: Add {C}. Spend this mana only to cast a colored spell without {X} in its mana cost.
        addCard(Zone.HAND, playerA, "Titans' Nest"); // Enchantment {1}{B}{G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Titans' Nest");                
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Titans' Nest", 1);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}[{TitansNestManaCondition}]", manaOptions);        
    }
}