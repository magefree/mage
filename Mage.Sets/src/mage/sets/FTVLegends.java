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

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FTVLegends extends ExpansionSet {

    private static final FTVLegends fINSTANCE = new FTVLegends();

    public static FTVLegends getInstance() {
        return fINSTANCE;
    }

    private FTVLegends() {
        super("From the Vault: Legends", "V11", "mage.sets.ftvlegends", ExpansionSet.buildDate(2011, 8, 26), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Cao Cao, Lord of Wei", 1, Rarity.MYTHIC, mage.cards.c.CaoCaoLordOfWei.class));
        cards.add(new SetCardInfo("Captain Sisay", 2, Rarity.MYTHIC, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 3, Rarity.MYTHIC, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Kiki-Jiki, Mirror Breaker", 4, Rarity.MYTHIC, mage.cards.k.KikiJikiMirrorBreaker.class));
        cards.add(new SetCardInfo("Kresh the Bloodbraided", 5, Rarity.MYTHIC, mage.cards.k.KreshTheBloodbraided.class));
        cards.add(new SetCardInfo("Mikaeus, the Lunarch", 6, Rarity.MYTHIC, mage.cards.m.MikaeusTheLunarch.class));
        cards.add(new SetCardInfo("Omnath, Locus of Mana", 7, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfMana.class));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 8, Rarity.MYTHIC, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Progenitus", 9, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Rafiq of the Many", 10, Rarity.MYTHIC, mage.cards.r.RafiqOfTheMany.class));
        cards.add(new SetCardInfo("Sharuum the Hegemon", 11, Rarity.MYTHIC, mage.cards.s.SharuumTheHegemon.class));
        cards.add(new SetCardInfo("Sun Quan, Lord of Wu", 12, Rarity.MYTHIC, mage.cards.s.SunQuanLordOfWu.class));
        cards.add(new SetCardInfo("Teferi, Mage of Zhalfir", 13, Rarity.MYTHIC, mage.cards.t.TeferiMageOfZhalfir.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", 14, Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Visara the Dreadful", 15, Rarity.MYTHIC, mage.cards.v.VisaraTheDreadful.class));
    }
}
