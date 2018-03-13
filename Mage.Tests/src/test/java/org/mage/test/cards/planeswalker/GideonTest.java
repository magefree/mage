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
package org.mage.test.cards.planeswalker;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GideonTest extends CardTestPlayerBase {

    // Gideon, Battle-Forged
    // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.
    // +1: Until your next turn, target creature gains indestructible. Untap that creature.
    // 0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
    @Test
    public void testGideonBattleForged() {
        // At end of combat, if Kytheon, Hero of Akros and at least two other creatures attacked this combat, exile Kytheon,
        // then return him to the battlefield transformed under his owner's control.
        // {2}{W}: Kytheon gains indestructible until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Kytheon, Hero of Akros");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        attack(2, playerB, "Kytheon, Hero of Akros");
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "+1: Until your next turn, target creature gains indestructible. Untap that creature.", "Silvercoat Lion");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Gideon, Battle-Forged", 1);
        assertCounterCount("Gideon, Battle-Forged", CounterType.LOYALTY, 4);
        assertLife(playerA, 14);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertAbility(playerB, "Silvercoat Lion", IndestructibleAbility.getInstance(), false);
    }

    /*
     * Reported bug: When Gideon, Champion of Justice uses his +0 ability to become a creature,
     * he is immediately sent to the grave instead.
     */
    @Test
    public void testGideonChampionOfJusticeSecondAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        /*
        Gideon, Champion of Justice {2}{W}{W} - 4 Loyalty
        +1: Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.

        0: Until end of turn, Gideon, Champion of Justice becomes a Human Soldier creature with power and toughness
        each equal to the number of loyalty counters on him and gains indestructible. He's still a planeswalker.
        Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability1 = new LoyaltyAbility(

        -15: Exile all other permanents.
         */
        addCard(Zone.HAND, playerA, "Gideon, Champion of Justice", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gideon, Champion of Justice");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+0: Until end of turn");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Put a loyalty counter on", playerB);

        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "+0: Until end of turn");

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Gideon, Champion of Justice", 0);
        assertPermanentCount(playerA, "Gideon, Champion of Justice", 1);
        assertCounterCount(playerA, "Gideon, Champion of Justice", CounterType.LOYALTY, 7);
        assertPowerToughness(playerA, "Gideon, Champion of Justice", 7, 7);

    }

    /**
     * When you use Gideon, Battle-Forged (flipped version of Kytheon from Magic
     * Origins) and use his 0 ability to turn him into a creature and equip the
     * Stitcher's Graft from EMN, he should have to be sacced at the end of
     * turn.
     */
    @Test
    public void testGideonBattleForgedSacrifice() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // At end of combat, if Kytheon, Hero of Akros and at least two other creatures attacked this combat, exile Kytheon,
        // then return him to the battlefield transformed under his owner's control.
        // {2}{W}: Kytheon gains indestructible until end of turn.
        // Gideon, Battle-Forged
        // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.
        // +1: Until your next turn, target creature gains indestructible. Untap that creature.
        // +0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        addCard(Zone.BATTLEFIELD, playerB, "Kytheon, Hero of Akros");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        // Equipped creature gets +3/+3.
        // Whenever equipped creature attacks, it doesn't untap during its controller's next untap step.
        // Whenever Stitcher's Graft becomes unattached from a permanent, sacrifice that permanent.
        // Equip {2}
        addCard(Zone.BATTLEFIELD, playerB, "Stitcher's Graft", 1);

        attack(2, playerB, "Kytheon, Hero of Akros");
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Pillarfield Ox");

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, "+0: Until ");
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {2}", "Gideon, Battle-Forged");
        attack(4, playerB, "Gideon, Battle-Forged"); // 7 damage

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertLife(playerA, 7);
        Permanent equipment = getPermanent("Stitcher's Graft", playerB);
        Assert.assertTrue("Stitcher's Graft may no longer be equipped", equipment.getAttachedTo() == null);
        assertPermanentCount(playerB, "Gideon, Battle-Forged", 0);
        assertGraveyardCount(playerB, "Kytheon, Hero of Akros", 1);
    }
}
