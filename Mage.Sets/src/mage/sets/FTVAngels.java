/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FTVAngels extends ExpansionSet {

    private static final FTVAngels instance = new FTVAngels();

    public static FTVAngels getInstance() {
        return instance;
    }

    private FTVAngels() {
        super("From the Vault: Angels", "V15", ExpansionSet.buildDate(2015, 8, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Akroma, Angel of Fury", 1, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 2, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfWrath.class));
        cards.add(new SetCardInfo("Archangel of Strife", 3, Rarity.MYTHIC, mage.cards.a.ArchangelOfStrife.class));
        cards.add(new SetCardInfo("Aurelia, the Warleader", 4, Rarity.MYTHIC, mage.cards.a.AureliaTheWarleader.class));
        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 5, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 6, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Entreat the Angels", 7, Rarity.MYTHIC, mage.cards.e.EntreatTheAngels.class));
        cards.add(new SetCardInfo("Exalted Angel", 8, Rarity.MYTHIC, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Iona, Shield of Emeria", 9, Rarity.MYTHIC, mage.cards.i.IonaShieldOfEmeria.class));
        cards.add(new SetCardInfo("Iridescent Angel", 10, Rarity.MYTHIC, mage.cards.i.IridescentAngel.class));
        cards.add(new SetCardInfo("Jenara, Asura of War", 11, Rarity.MYTHIC, mage.cards.j.JenaraAsuraOfWar.class));
        cards.add(new SetCardInfo("Lightning Angel", 12, Rarity.MYTHIC, mage.cards.l.LightningAngel.class));
        cards.add(new SetCardInfo("Platinum Angel", 13, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Serra Angel", 14, Rarity.MYTHIC, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Tariel, Reckoner of Souls", 15, Rarity.MYTHIC, mage.cards.t.TarielReckonerOfSouls.class));
    }
}
