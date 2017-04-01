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
package org.mage.test.cards.replacement.entersBattlefield;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HardenedScaleTest extends CardTestPlayerBase {

    /*
     Reported bug: Hangarback interaciton with Hardened Scales and Metallic Mimic on board is incorrect.
     */
    @Test
    public void hangarBackHardenedScalesMetallicMimicTest() {

        /*
          Hangarback Walker {X}{X}
        Artifact Creature — Construct 0/0
        Hangarback Walker enters the battlefield with X +1/+1 counters on it.
        When Hangarback Walker dies, create a 1/1 colorless Thopter artifact creature token with flying for each +1/+1 counter on Hangarback Walker.
        {1}, {T}: Put a +1/+1 counter on Hangarback Walker.
         */
        String hWalker = "Hangarback Walker";

        /*
        Hardened Scales {G}
        Enchantment
        If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.
         */
        String hScales = "Hardened Scales";

        /*
         Metallic Mimic {2}
        Artifact Creature — Shapeshifter 2/1
        As Metallic Mimic enters the battlefield, choose a creature type.
        Metallic Mimic is the chosen type in addition to its other types.
        Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
         */
        String mMimic = "Metallic Mimic";

        addCard(Zone.BATTLEFIELD, playerA, hScales);
        addCard(Zone.HAND, playerA, mMimic);
        addCard(Zone.HAND, playerA, hWalker);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mMimic);
        setChoice(playerA, "Construct");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hWalker);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, mMimic, 1);
        assertPermanentCount(playerA, hWalker, 1);
        assertCounterCount(playerA, hWalker, CounterType.P1P1, 3);
        assertPowerToughness(playerA, hWalker, 3, 3);
    }

    @Test
    public void testWithVigorMortis() {

        /*
        Vigor Mortis {2}{B}{B}
        Sorcery
        Return target creature card from your graveyard to the battlefield. If {G} was spent to cast Vigor Mortis,
        that creature enters the battlefield with an additional +1/+1 counter on it.
         */
        String vMortis = "Vigor Mortis";

        /*
        Hardened Scales {G}
        Enchantment
        If one or more +1/+1 counters would be placed on a creature you control, that many plus one +1/+1 counters are placed on it instead.
         */
        String hScales = "Hardened Scales";

        /*
         Metallic Mimic {2}
        Artifact Creature — Shapeshifter 2/1
        As Metallic Mimic enters the battlefield, choose a creature type.
        Metallic Mimic is the chosen type in addition to its other types.
        Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
         */
        String mMimic = "Metallic Mimic";

        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, hScales);
        addCard(Zone.HAND, playerA, mMimic);
        addCard(Zone.HAND, playerA, vMortis);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mMimic);
        setChoice(playerA, "Cat");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, vMortis, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, mMimic, 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 3);
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5); // Hardened Scales is only once applied to EntersTheBattlefield event
        assertGraveyardCount(playerA, vMortis, 1);
    }
}
