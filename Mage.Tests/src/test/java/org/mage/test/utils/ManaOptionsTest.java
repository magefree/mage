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
package org.mage.test.utils;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * This test checks if the calculated possible mana options are correct related to the given mana sources available.
 * 
 * @author LevelX2
 */
public class ManaOptionsTest extends CardTestPlayerBase {

    @Test
    public void testSimpleMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{G}{G}{G}", getManaOption(0, manaOptions));

    }

    // Tinder Farm enters the battlefield tapped.
    // {T}: Add {G} to your mana pool.
    // {T}, Sacrifice Tinder Farm: Add {R}{W} to your mana pool.
    @Test
    public void testTinderFarm() {
        addCard(Zone.BATTLEFIELD, playerA, "Tinder Farm", 3);

        setStopAt(2, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",4, manaOptions.size());
        Assert.assertEquals("{G}{G}{G}", getManaOption(0, manaOptions));
        Assert.assertEquals("{R}{G}{G}{W}", getManaOption(1, manaOptions));
        Assert.assertEquals("{R}{R}{G}{W}{W}", getManaOption(2, manaOptions));
        Assert.assertEquals("{R}{R}{R}{W}{W}{W}", getManaOption(3, manaOptions));

    }

    // Adarkar Wastes
    // {T}: Add {1} to your mana pool.
    // {T}: Add {W} or {U} to your mana pool. Adarkar Wastes deals 1 damage to you.
    @Test
    public void testAdarkarWastes() {
        addCard(Zone.BATTLEFIELD, playerA, "Adarkar Wastes", 3);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",4, manaOptions.size());
        Assert.assertEquals("{W}{W}{W}", getManaOption(0, manaOptions));
        Assert.assertEquals("{U}{W}{W}", getManaOption(1, manaOptions));
        Assert.assertEquals("{U}{U}{W}", getManaOption(2, manaOptions));
        Assert.assertEquals("{U}{U}{U}", getManaOption(3, manaOptions));

    }


    // Chromatic Sphere
    // {1}, {T}, Sacrifice Chromatic Sphere: Add one mana of any color to your mana pool. Draw a card.
    @Test
    public void testChromaticSphere() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Sphere", 2);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{Any}{Any}", getManaOption(0, manaOptions));
    }

    // Orochi Leafcaller
    // {G}: Add one mana of any color to your mana pool.
    @Test
    public void testOrochiLeafcaller() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Orochi Leafcaller", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{W}{W}{Any}{Any}", getManaOption(0, manaOptions));
    }

    // Crystal Quarry
    // {T}: {1} Add  to your mana pool.
    // {5}, {T}: Add {W}{U}{B}{R}{G} to your mana pool.
    @Test
    public void testCrystalQuarry() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Quarry", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{1}{G}{G}{W}{W}", getManaOption(0, manaOptions));
    }
    // Crystal Quarry
    // {T}: {1} Add  to your mana pool.
    // {5}, {T}: Add {W}{U}{B}{R}{G} to your mana pool.
    @Test
    public void testCrystalQuarry2() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Quarry", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",2, manaOptions.size());
        Assert.assertEquals("{1}{G}{G}{G}{W}{W}", getManaOption(0, manaOptions));
        Assert.assertEquals("{R}{G}{U}{W}{B}", getManaOption(1, manaOptions));
    }

    // Nykthos, Shrine to Nyx
    // {T}: Add {1} to your mana pool.
    // {2}, {T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. (Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)
    @Test
    public void testNykthos1() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{G}{G}{G}{G}{G}", getManaOption(0, manaOptions));
    }
    
    @Test
    public void testNykthos2() {
        addCard(Zone.BATTLEFIELD, playerA, "Sedge Scorpion", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Akroan Crusader", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",2, manaOptions.size());
        Assert.assertEquals("{G}{G}{G}{G}{G}", getManaOption(0, manaOptions));
        Assert.assertEquals("{R}{R}{R}{G}", getManaOption(1, manaOptions));
    }

    @Test
    public void testNykthos3() {
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Caryatid", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",1, manaOptions.size());
        Assert.assertEquals("{1}{G}{Any}", getManaOption(0, manaOptions));
    }

    @Test
    public void testMix1() {
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Star", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Sphere", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grove of the Burnwillows", 1);

        setStopAt(1, PhaseStep. UPKEEP);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assert.assertEquals("mana variations don't fit",2, manaOptions.size());
        Assert.assertEquals("{Any}{Any}", getManaOption(0, manaOptions));
        Assert.assertEquals("{Any}{Any}", getManaOption(1, manaOptions));
    }

    private     String getManaOption(int index, ManaOptions manaOptions) {
        if (manaOptions.size() < index + 1) {
            return "";
        }
        return manaOptions.get(index).toString();
    }
}