
package org.mage.test.cards.single.lrw;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PlainswalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Zone;
import mage.filter.FilterCard;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author psykad
 */
public class CairnWandererTest  extends CardTestPlayerBase {
    
    /*
     * Testing: As long as a creature card with flying is in a graveyard, 
     * {this} has flying. The same is true for fear, first strike, 
     * double strike, deathtouch, haste, landwalk, lifelink, protection, 
     * reach, trample, shroud, and vigilance.
     */
    @Test
    public void TestCairnWandererEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Cairn Wanderer");
        
        // Testing FlyingAbility.
        addCard(Zone.GRAVEYARD, playerA, "Lantern Kami");        
        
        // Testing FearAbility.
        addCard(Zone.GRAVEYARD, playerA, "Prickly Boggart");
        
        // Testing FirstStrikeAbility.
        addCard(Zone.GRAVEYARD, playerA, "Serra Zealot");
        
        // Testing DoubleStrikeAbility.
        addCard(Zone.GRAVEYARD, playerA, "Fencing Ace");
        
        // Testing DeathtouchAbility.
        addCard(Zone.GRAVEYARD, playerA, "Typhoid Rats");
        
        // Testing HasteAbility.
        addCard(Zone.GRAVEYARD, playerB, "Raging Goblin");
        
        // Testing LandwalkAbility.
        addCard(Zone.GRAVEYARD, playerB, "Zodiac Rooster");
        
        // Testing LifelinkAbility.
        addCard(Zone.GRAVEYARD, playerB, "Trained Caracal");
        
        // Testing ProtectionAbility.
        addCard(Zone.GRAVEYARD, playerB, "Progenitus");
        
        // Testing ReachAbility.
        addCard(Zone.GRAVEYARD, playerB, "Tree Monkey");
        
        // Testing TrampleAbility.
        addCard(Zone.GRAVEYARD, playerB, "Defiant Elf");
        
        // Testing ShroudAbility.
        addCard(Zone.GRAVEYARD, playerB, "Elvish Lookout");
        
        // Testing VigilanceAbility.
        addCard(Zone.GRAVEYARD, playerB, "Veteran Cavalier");
        
        execute();
        
        List<Ability> abilities = new ArrayList<>();        
        abilities.add(FlyingAbility.getInstance());
        abilities.add(FearAbility.getInstance());
        abilities.add(FirstStrikeAbility.getInstance());
        abilities.add(DoubleStrikeAbility.getInstance());
        abilities.add(DeathtouchAbility.getInstance());
        abilities.add(HasteAbility.getInstance());
        abilities.add(LifelinkAbility.getInstance());
        abilities.add(ReachAbility.getInstance());
        abilities.add(ShroudAbility.getInstance());
        abilities.add(TrampleAbility.getInstance());
        abilities.add(VigilanceAbility.getInstance());  
        assertAbilities(playerA, "Cairn Wanderer", abilities);
        assertAbility(playerA, "Cairn Wanderer", new PlainswalkAbility(), true);
        assertAbility(playerA, "Cairn Wanderer", new ProtectionAbility(new FilterCard("everything")), true); // Progenitus - protection from everything.
    }
}