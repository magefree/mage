/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
        addCard(Zone.GRAVEYARD, playerA, "Raging Goblin");
        
        // Testing LandwalkAbility.
        addCard(Zone.GRAVEYARD, playerA, "Zodiac Rooster");
        
        // Testing LifelinkAbility.
        addCard(Zone.GRAVEYARD, playerA, "Trained Caracal");
        
        // Testing ProtectionAbility.
        addCard(Zone.GRAVEYARD, playerA, "Progenitus");
        
        // Testing ReachAbility.
        addCard(Zone.GRAVEYARD, playerA, "Tree Monkey");
        
        // Testing TrampleAbility.
        addCard(Zone.GRAVEYARD, playerA, "Defiant Elf");
        
        // Testing ShroudAbility.
        addCard(Zone.GRAVEYARD, playerA, "Elvish Lookout");
        
        // Testing VigilanceAbility.
        addCard(Zone.GRAVEYARD, playerA, "Veteran Cavalier");
        
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