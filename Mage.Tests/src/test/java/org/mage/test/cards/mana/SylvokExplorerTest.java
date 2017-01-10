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
package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SylvokExplorerTest extends CardTestPlayerBase {

    /**
     * java.lang.StackOverflowError at
     * mage.filter.predicate.Predicates.and(Predicates.java:68) at
     * mage.filter.FilterImpl.match(FilterImpl.java:62) at
     * mage.filter.FilterPermanent.match(FilterPermanent.java:74) at
     * mage.game.permanent.Battlefield.getActivePermanents(Battlefield.java:362)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getManaTypes(AnyColorLandsProduceManaAbility.java:164)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getNetMana(AnyColorLandsProduceManaAbility.java:181)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaAbility.getNetMana(AnyColorLandsProduceManaAbility.java:70)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getManaTypes(AnyColorLandsProduceManaAbility.java:170)
     * at
     * mage.abilities.mana.AnyColorLandsProduceManaEffect.getNetMana(AnyColorLandsProduceManaAbility.java:181)
     */
    @Test
    public void testOneInstance() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 1 red and 1 white mana", "{R}{W}", options.get(0).toString());
        Assert.assertEquals("Player should be able to create 1 blue and 1 white mana", "{U}{W}", options.get(1).toString());
    }

    @Test
    public void testTwoInstances() {
        addCard(Zone.BATTLEFIELD, playerB, "Exotic Orchard", 2);

        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 3 white mana", "{W}{W}{W}", options.get(0).toString());
    }
}
