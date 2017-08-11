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
 * @author Saga
 */
public class HasconPromo2017 extends ExpansionSet {
    private static final HasconPromo2017 instance = new HasconPromo2017();

    public static HasconPromo2017 getInstance() {
        return instance;
    }

    private HasconPromo2017() {
        super("HASCON Promo 2017", "H17", ExpansionSet.buildDate(2017, 9, 8), SetType.JOKESET);
        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Dinobot Leader", 1, Rarity.MYTHIC, mage.cards.g.GrimlockDinobotLeader.class));
        cards.add(new ExpansionSet.SetCardInfo("Grimlock, Ferocious King", 1, Rarity.MYTHIC, mage.cards.g.GrimlockFerociousKing.class));
        cards.add(new ExpansionSet.SetCardInfo("Sword of Dungeons & Dragons", 3, Rarity.MYTHIC, mage.cards.s.SwordOfDungeonsAndDragons.class));
    }
}
