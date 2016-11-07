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
package org.mage.test.token;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ActivatedAbilityTest extends CardTestPlayerBase {

    /**
     * Check that activated ability of created token works
     */
    @Test
    public void testActivatedManaAbility() {
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Freyalise, Llanowar's Fury");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Create a 1/1 green Elf Druid creature token with \"{T}: Add {G} to your mana pool.\"");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "+2: Create a 1/1 green Elf Druid creature token with \"{T}: Add {G} to your mana pool.\"");
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G} to your mana pool.");
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elf Druid", 2);
        assertPermanentCount(playerA, "Freyalise, Llanowar's Fury", 1);
        Assert.assertEquals("one green mana has to be in the mana pool", 1, playerA.getManaPool().get(ManaType.GREEN));
    }
}
