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
public class HarvestMageTest extends CardTestPlayerBase {

    @Test
    public void testOneInstance() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        // {G}, {T}, Discard a card: Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount.
        addCard(Zone.HAND, playerA, "Harvest Mage", 1); // Creature 1/1 {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harvest Mage");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard a card: Until end of turn");
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Harvest Mage", 1);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}", manaOptions);        
    }
    
    @Test
    public void test_AncientTomb() {
        setStrictChooseMode(true);
        
        
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Tomb", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");

        // {G}, {T}, Discard a card: Until end of turn, if you tap a land for mana, it produces one mana of a color of your choice instead of any other type and amount.
        addCard(Zone.HAND, playerA, "Harvest Mage", 1); // Creature 1/1 {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harvest Mage");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}, {T}, Discard a card: Until end of turn");
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Harvest Mage", 1);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);        
    }    
}
