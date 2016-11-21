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
package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SwordOfTheMeekTest extends CardTestPlayerBase {

    /**
     * Played a game vs. the AI when I noticed the following:
     *
     * Had Master of Etherium and Myrsmith in play, Sword of the Meek in the
     * graveyard. I cast an artifact spell, Myrsmith triggers. I pay {1} to get
     * a Myr token which enters the battlefield as a 2/2 (due to Master of
     * Etherium), but Sword of the Meek triggers regardless and I can have it
     * enter the battlefield attached to the token. Also, Myrsmith's trigger
     * wasn't optional, it didn't ask whether I wanted to pay or not.
     */
    @Test
    public void testEquipAlive() {
        // Master of Etherium's power and toughness are each equal to the number of artifacts you control.
        // Other artifact creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Master of Etherium", 1); // Creature 1/1

        // Whenever you cast an artifact spell, you may pay . If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Myrsmith", 1); // Creature 1/1

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Chromatic Star");

        // Equipped creature gets +1/+2.
        // Equip {2}
        // Whenever a 1/1 creature enters the battlefield under your control, you may return Sword of the Meek from your graveyard to the battlefield, then attach it to that creature.
        addCard(Zone.GRAVEYARD, playerA, "Sword of the Meek");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chromatic Star");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chromatic Star", 1);

        assertPermanentCount(playerA, "Myr", 1);
        assertPowerToughness(playerA, "Myr", 2, 2);
        assertPermanentCount(playerA, "Sword of the Meek", 0);
        assertPowerToughness(playerA, "Master of Etherium", 3, 3);

        Permanent myr = getPermanent("Myr", playerA.getId());
        Assert.assertTrue("Myr may not have any attachments", myr.getAttachments().isEmpty());
    }

}
