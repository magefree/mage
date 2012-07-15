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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class HellionCrucible extends CardImpl<HellionCrucible> {

    public HellionCrucible(UUID ownerId) {
        super(ownerId, 226, "Hellion Crucible", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "M13";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        
        // {1}{R}, {tap}: Put a pressure counter on Hellion Crucible.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PRESSURE.createInstance()), new ManaCostsImpl("{1}{R}")));
        
        // {1}{R}, {tap}, Remove two pressure counters from Hellion Crucible and sacrifice it: Put a 4/4 red Hellion creature token with haste onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new HellionToken(), 1), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.PRESSURE.createInstance(2)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public HellionCrucible(final HellionCrucible card) {
        super(card);
    }

    @Override
    public HellionCrucible copy() {
        return new HellionCrucible(this);
    }
}

class HellionToken extends Token {
    public HellionToken() {
        super("Hellion", "4/4 red Hellion creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Hellion");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(HasteAbility.getInstance());
    }
}
