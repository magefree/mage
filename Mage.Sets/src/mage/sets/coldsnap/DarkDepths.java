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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author Plopman
 */
public class DarkDepths extends CardImpl<DarkDepths> {

    public DarkDepths(UUID ownerId) {
        super(ownerId, 145, "Dark Depths", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "CSP";
        this.supertype.add("Legendary");
        this.supertype.add("Snow");

        // Dark Depths enters the battlefield with ten ice counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.ICE.createInstance(10)), "with ten ice counters on it"));
        // {3}: Remove an ice counter from Dark Depths.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.ICE.createInstance(1)), new ManaCostsImpl("{3}")));
        // When Dark Depths has no ice counters on it, sacrifice it. If you do, put a legendary 20/20 black Avatar creature token with flying and "This creature is indestructible" named Marit Lage onto the battlefield.
        this.addAbility(new DarkDepthsAbility());
    }

    public DarkDepths(final DarkDepths card) {
        super(card);
    }

    @Override
    public DarkDepths copy() {
        return new DarkDepths(this);
    }
}

class DarkDepthsAbility extends StateTriggeredAbility<DarkDepthsAbility> {

    public DarkDepthsAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.addEffect(new CreateTokenEffect(new MaritLageToken()));
    }

    public DarkDepthsAbility(final DarkDepthsAbility ability) {
        super(ability);
    }

    @Override
    public DarkDepthsAbility copy() {
        return new DarkDepthsAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if(permanent != null && permanent.getCounters().getCount(CounterType.ICE) == 0){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When Dark Depths has no ice counters on it, sacrifice it. If you do, put a legendary 20/20 black Avatar creature token with flying and \"This creature is indestructible\" named Marit Lage onto the battlefield";
    }

}


class MaritLageToken extends Token {

    public MaritLageToken() {
        super("Marit Lage", "legendary 20/20 black Avatar creature token with flying and \"This creature is indestructible\" named Marit Lage");
        cardType.add(CardType.CREATURE);
        subtype.add("Avatar");
        supertype.add("Legendary");
        

        color = ObjectColor.BLACK;
        power = new MageInt(20);
        toughness = new MageInt(20);
        
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new IndestructibleAbility());
    }
}