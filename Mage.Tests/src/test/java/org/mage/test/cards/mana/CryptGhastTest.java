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
public class CryptGhastTest extends CardTestPlayerBase {

    @Test
    public void TestNormal() {
        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        // Whenever you tap a Swamp for mana, add {B} to your mana pool (in addition to the mana the land produces).
        addCard(Zone.BATTLEFIELD, playerA, "Crypt Ghast", 1);
        addCard(Zone.HAND, playerA, "Erebos's Titan", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Swamp Mountain
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R} to your mana pool");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B} to your mana pool");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Erebos's Titan");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Erebos's Titan", 1);

    }

    /**
     * Crypt Ghast was still effective (doubling swamp {B} Mana and providiong
     * the option to extort) as if it was on the battlefield after being killed
     * with Nin, the Pain Artist controlled by me and then exiled into a Mimic
     * Vat controlled by Crypt Ghast's controller.
     */
    @Test
    public void TestExiled() {
        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        // Whenever you tap a Swamp for mana, add {B} to your mana pool (in addition to the mana the land produces).
        addCard(Zone.BATTLEFIELD, playerA, "Crypt Ghast", 1);
        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        // {3},{T}: Put a token onto the battlefield that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Mimic Vat", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Swamp Mountain
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 1);
        addCard(Zone.HAND, playerA, "Erebos's Titan", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        // {X}{U}{R},{T}: Nin, the Pain Artist deals X damage to target creature. That creature's controller draws X cards.
        addCard(Zone.BATTLEFIELD, playerB, "Nin, the Pain Artist", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{X}{U}{R},{T}: {this} deals X damage to target creature", "Crypt Ghast");
        setChoice(playerB, "X=2");

        // Crypt Ghast may no longer give additional mana
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R} to your mana pool");
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B} to your mana pool");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Erebos's Titan");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Erebos's Titan", 0);
        assertTapped("Nin, the Pain Artist", true);
        assertExileCount("Crypt Ghast", 1);

    }
}
