package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

public class DoublingCubeTest extends CardTestPlayerBase {

    // {3}, {T}: Double the amount of each type of mana in your mana pool.
    String cube = "Doubling Cube";
    // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
    String temple = "Eldrazi Temple";
    // Mana pools don't empty as steps and phases end.
    String upwelling = "Upwelling";

    //issue 3443
    @Test
    public void test_DoublingCubeEldraziTemple() {

        addCard(Zone.BATTLEFIELD, playerA, temple);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, cube);
        addCard(Zone.BATTLEFIELD, playerA, upwelling);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, {T}:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertManaPool(playerA, ManaType.COLORLESS, 4);
        assertAllCommandsUsed();
    }
    
    @Test
    public void test_AvailableMana() {
        setStrictChooseMode(true);
        
        // {3}, {T}: Double the amount of each type of mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, cube);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        
        execute();
        
        assertAllCommandsUsed();
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);        
    }    

    @Test
    public void test_AvailableMana2() {
        setStrictChooseMode(true);
        
        // {3}, {T}: Double the amount of each type of mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, cube, 2);
        // {T}: Add Colorless.
        // {1}, {T}: Add Black.
        // {2}, {T}: Add Blue or Red.        
        addCard(Zone.BATTLEFIELD, playerA, "Castle Sengir", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        
        setStopAt(1, PhaseStep.UPKEEP);
        
        execute();
        
        assertAllCommandsUsed();
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 138, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{R}{R}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{R}{R}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{B}{B}{B}{B}{G}{G}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{U}{U}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{U}{U}{U}{U}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{U}{U}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{G}{G}{U}{U}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{U}{U}{U}{U}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{G}{G}{U}{U}{U}{U}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{G}{G}{G}{G}{U}{U}{R}{R}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{G}{G}{G}{G}{G}{G}{U}{U}", manaOptions);
        assertManaOptions("{C}{C}{C}{C}{G}{G}{G}{G}{G}{G}{U}{U}{U}{U}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{R}{R}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{R}{R}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{B}{B}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{B}{B}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{B}{B}{B}{B}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{U}{U}{B}{B}{R}{R}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{U}{U}{U}{U}{B}{B}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{U}{U}{B}{B}{B}{B}{R}{R}{R}{R}", manaOptions);
        assertManaOptions("{U}{U}{U}{U}{B}{B}{B}{B}{R}{R}", manaOptions);
        assertManaOptions("{U}{U}{B}{B}{R}{R}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{U}{U}{B}{B}{R}{R}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{U}{U}{U}{U}{B}{B}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{G}{G}{G}{G}{U}{U}{U}{U}", manaOptions);
        assertManaOptions("{U}{U}{B}{B}{B}{B}{R}{R}{G}{G}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{G}{G}{G}{G}{U}{U}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}{G}{G}{U}{U}{U}{U}", manaOptions);
        assertManaOptions("{B}{B}{G}{G}{G}{G}{G}{G}{U}{U}", manaOptions);
        assertManaOptions("{U}{U}{U}{U}{U}{U}", manaOptions);
    }    
    
}
