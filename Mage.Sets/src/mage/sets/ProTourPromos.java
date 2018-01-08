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
 * @author JayDi85
 */
public class ProTourPromos extends ExpansionSet {

    private static final ProTourPromos instance = new ProTourPromos();

    public static ProTourPromos getInstance() {
        return instance;
    }

    private ProTourPromos() {
        super("Pro Tour Promos", "PPRO", ExpansionSet.buildDate(2007, 2, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        // https://mtg.gamepedia.com/Promotional_cards#Pro_tour_cards
        cards.add(new SetCardInfo("Ajani Goldmane", 2011, Rarity.MYTHIC, mage.cards.a.AjaniGoldmane.class));
        cards.add(new SetCardInfo("Avatar of Woe", 2010, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 2017, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Eternal Dragon", 2007, Rarity.RARE, mage.cards.e.EternalDragon.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 2015, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Mirari's Wake", 2008, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Noble Hierarch", 2018, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 2016, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Treva, the Renewer", 2009, Rarity.RARE, mage.cards.t.TrevaTheRenewer.class));
    }

}
