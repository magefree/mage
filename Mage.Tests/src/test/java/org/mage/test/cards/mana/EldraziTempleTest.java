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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EldraziTempleTest extends CardTestPlayerBase {

    @Test
    public void TestWithoutDampingSphere() {
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Temple", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Devoid (This card has no color.)
        // {2}{R}, Sacrifice another colorless creature: Barrage Tyrant deals damage equal to the sacrificed creature's power to target creature or player.
        addCard(Zone.HAND, playerA, "Barrage Tyrant", 1); // Creature {4}{R}

        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        // addCard(Zone.BATTLEFIELD, playerB, "Damping Sphere", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barrage Tyrant");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Barrage Tyrant", 1);

    }

    /**
     * Damping Sphere vs Eldrazi
     *
     * My opponent managed to produce 7 mana to cast a All Is Dust with 6 lands
     * where one was an Eldrazi Temple. This should not be possible if my
     * reading of Damping Sphere (If a land is tapped for two or more mana, it
     * produces {C} instead of any other type and amount.) is correct. It should
     * produce a single colorless mana rather than 2 colorless for the eldrazi
     * tribal spell
     */
    @Test
    public void TestWithDampingSphere() {
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Temple", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Devoid (This card has no color.)
        // {2}{R}, Sacrifice another colorless creature: Barrage Tyrant deals damage equal to the sacrificed creature's power to target creature or player.
        addCard(Zone.HAND, playerA, "Barrage Tyrant", 1); // Creature {4}{R}

        // If a land is tapped for two or more mana, it produces {C} instead of any other type and amount.
        // Each spell a player casts costs {1} more to cast for each other spell that player has cast this turn.
        addCard(Zone.BATTLEFIELD, playerB, "Damping Sphere", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barrage Tyrant");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Barrage Tyrant", 0);
    }

    @Test
    public void TestUseOfUnconditionalMana() {
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi.
        addCard(Zone.BATTLEFIELD, playerA, "Eldrazi Temple", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // {T}: Add {W}, {B}, or {G}.
        // {W}{B}{G}, {T}, Sacrifice Abzan Banner: Draw a card.
        addCard(Zone.HAND, playerA, "Abzan Banner", 1); // Artifact {3}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abzan Banner");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Abzan Banner", 1);
    }
}
