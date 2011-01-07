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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class CarnifexDemon extends CardImpl<CarnifexDemon> {

    public CarnifexDemon (UUID ownerId) {
        super(ownerId, 57, "Carnifex Demon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Demon");
		this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "Carnifex Demon enters the battlefield with two -1/-1 counters on it"));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CarnifexDemonEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
        this.addAbility(ability);
    }

    public CarnifexDemon (final CarnifexDemon card) {
        super(card);
    }

    @Override
    public CarnifexDemon copy() {
        return new CarnifexDemon(this);
    }
}

class CarnifexDemonEffect extends OneShotEffect<CarnifexDemonEffect> {
    public CarnifexDemonEffect() {
        super(Constants.Outcome.UnboostCreature);
    }

    public CarnifexDemonEffect(final CarnifexDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            for (Permanent t : game.getBattlefield().getAllActivePermanents()) {
                if (t.getCardType().contains(CardType.CREATURE) && !t.getId().equals(source.getSourceId()))
                    t.addCounters(CounterType.M1M1.createInstance());
            }
        }
        return false;
    }

    @Override
    public CarnifexDemonEffect copy() {
        return new CarnifexDemonEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Put a -1/-1 counter on each other creature";
    }
}