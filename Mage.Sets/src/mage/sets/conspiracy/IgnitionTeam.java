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
package mage.sets.conspiracy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class IgnitionTeam extends CardImpl {
    
    public IgnitionTeam(UUID ownerId) {
        super(ownerId, 34, "Ignition Team", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ignition Team enters the battlefield with X +1/+1 counters on it, where X is the number of tapped lands on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0),
                new TappedLandsCount(), true), 
                "with X +1/+1 counters on it, where X is the number of tapped lands on the battlefield."));
        
        // {2}{R}, Remove a +1/+1 counter from Ignition Team: Target land becomes a 4/4 red Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new IgnitionTeamToken(), false, true, Duration.EndOfTurn), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
        
    }

    public IgnitionTeam(final IgnitionTeam card) {
        super(card);
    }

    @Override
    public IgnitionTeam copy() {
        return new IgnitionTeam(this);
    }
}

class TappedLandsCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            FilterLandPermanent filter = new FilterLandPermanent("tapped lands on the battlefield");
            filter.add(new TappedPredicate());
            return game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new TappedLandsCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "tapped lands on the battlefield";
    }
}

class IgnitionTeamToken extends Token {

    public IgnitionTeamToken() {
        super("", "4/4 red Elemental creature");
        this.cardType.add(CardType.CREATURE);
        this.getSubtype().add("Elemental");
        this.color.setRed(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }
}