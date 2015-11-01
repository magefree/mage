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
package org.mage.test.cards.abilities.activated;

import mage.abilities.common.LicidAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author emerald0000
 */

public class LicidAbilityTest extends CardTestPlayerBase {

    /**
     * Activate on another creature
     */
    @Test
    public void BasicUsageTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        
        execute();

        assertAbility(playerA, "Pillarfield Ox", HasteAbility.getInstance(), true);
        assertAbility(playerA, "Enraging Licid", new LicidAbility(new ColoredManaCost(ColoredManaSymbol.R), new ColoredManaCost(ColoredManaSymbol.R)), false);
        assertType("Enraging Licid", CardType.ENCHANTMENT, true);
        assertType("Enraging Licid", CardType.CREATURE, false);
    }
    
    /**
     * Use special action to remove the continuous effect
     */
    @Test
    @Ignore("Test player can't activate special actions yet")
    public void SpecialActionTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: End");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        execute();

        assertActionCount(playerA, 0);
        assertAbility(playerA, "Pillarfield Ox", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Enraging Licid", new LicidAbility(new ColoredManaCost(ColoredManaSymbol.R), new ColoredManaCost(ColoredManaSymbol.R)), true);
        assertType("Enraging Licid", CardType.ENCHANTMENT, false);
        assertType("Enraging Licid", CardType.CREATURE, true);
    }
    
    /**
     * Licid should die if enchanted creature dies
     */
    @Test
    @Ignore("Enraging Licid doesn't die when its enchanted creature dies due to similarity to Bestow")
    public void EnchantedCreatureDiesTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        // Destroy target nonblack creature.
        addCard(Zone.HAND, playerB, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        
        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Pillarfield Ox");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        
        execute();

        assertGraveyardCount(playerA, 2); // Pillarfield Ox + Enraging Licid
    }
}
