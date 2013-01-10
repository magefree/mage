/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public class GyreSage extends CardImpl<GyreSage> {

    public GyreSage(UUID ownerId) {
        super(ownerId, 123, "Gyre Sage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());
        
        //{T} : Add {G} to your mana pool for each +1/+1 counter on Gyre Sage.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana, new CountersCount(CounterType.P1P1)));
    }

    public GyreSage(final GyreSage card) {
        super(card);
    }

    @Override
    public GyreSage copy() {
        return new GyreSage(this);
    }
}
