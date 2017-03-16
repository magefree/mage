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
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;
import mage.constants.Rarity;

/**
 *
 * @author fireshoes
 */

public class CommandersArsenal extends ExpansionSet {

    private static final CommandersArsenal instance = new CommandersArsenal();

    public static CommandersArsenal getInstance() {
        return instance;
    }

    private CommandersArsenal() {
        super("Commander's Arsenal", "CM1", ExpansionSet.buildDate(2012, 11, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        cards.add(new SetCardInfo("Chaos Warp", 1, Rarity.SPECIAL, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Command Tower", 2, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Decree of Pain", 3, Rarity.SPECIAL, mage.cards.d.DecreeOfPain.class));
        cards.add(new SetCardInfo("Desertion", 4, Rarity.SPECIAL, mage.cards.d.Desertion.class));
        cards.add(new SetCardInfo("Diaochan, Artful Beauty", 5, Rarity.SPECIAL, mage.cards.d.DiaochanArtfulBeauty.class));
        cards.add(new SetCardInfo("Dragonlair Spider", 6, Rarity.SPECIAL, mage.cards.d.DragonlairSpider.class));
        cards.add(new SetCardInfo("Duplicant", 7, Rarity.SPECIAL, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Edric, Spymaster of Trest", 8, Rarity.SPECIAL, mage.cards.e.EdricSpymasterOfTrest.class));
        cards.add(new SetCardInfo("Kaalia of the Vast", 9, Rarity.SPECIAL, mage.cards.k.KaaliaOfTheVast.class));
        cards.add(new SetCardInfo("Loyal Retainers", 10, Rarity.SPECIAL, mage.cards.l.LoyalRetainers.class));
        cards.add(new SetCardInfo("Maelstrom Wanderer", 11, Rarity.SPECIAL, mage.cards.m.MaelstromWanderer.class));
        cards.add(new SetCardInfo("Mind's Eye", 13, Rarity.SPECIAL, mage.cards.m.MindsEye.class));
        cards.add(new SetCardInfo("Mirari's Wake", 14, Rarity.SPECIAL, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Rhystic Study", 15, Rarity.COMMON, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Scroll Rack", 16, Rarity.SPECIAL, mage.cards.s.ScrollRack.class));
        cards.add(new SetCardInfo("Sylvan Library", 17, Rarity.SPECIAL, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("The Mimeoplasm", 12, Rarity.SPECIAL, mage.cards.t.TheMimeoplasm.class));
        cards.add(new SetCardInfo("Vela the Night-Clad", 18, Rarity.SPECIAL, mage.cards.v.VelaTheNightClad.class));
    }

}
