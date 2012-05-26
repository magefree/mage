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
package mage.sets.lorwyn;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.YourTurnCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class HoofprintsOfTheStag extends CardImpl<HoofprintsOfTheStag> {

    public HoofprintsOfTheStag(UUID ownerId) {
        super(ownerId, 21, "Hoofprints of the Stag", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Tribal");
        this.subtype.add("Elemental");
        this.color.setWhite(true);
        this.addAbility(new DrawCardTriggeredAbility(new AddCountersSourceEffect(CounterType.HOOFPRINT.createInstance(1)), true));
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new WhiteElementalToken(), 1), new ManaCostsImpl("{2}{W}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.HOOFPRINT.createInstance(4)));
        ability.addCost(new YourTurnCost());
        this.addAbility(ability);
    }

    public HoofprintsOfTheStag(final HoofprintsOfTheStag card) {
        super(card);
    }

    @Override
    public HoofprintsOfTheStag copy() {
        return new HoofprintsOfTheStag(this);
    }
}

class WhiteElementalToken extends Token {
    WhiteElementalToken() {
        super("Elemental", "4/4 white Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Elemental");
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
    }
}
