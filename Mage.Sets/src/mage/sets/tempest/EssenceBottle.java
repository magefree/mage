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
package mage.sets.tempest;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class EssenceBottle extends CardImpl<EssenceBottle> {

    public EssenceBottle(UUID ownerId) {
        super(ownerId, 276, "Essence Bottle", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "TMP";
        // {3}, {tap}: Put an elixir counter on Essence Bottle.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.ELIXIR.createInstance()),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {tap}, Remove all elixir counters from Essence Bottle: You gain 2 life for each elixir counter removed this way.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EssenceBottleEffect(), new TapSourceCost());
        ability.addCost(new EssenceBottleCost());
        this.addAbility(ability);
    }

    public EssenceBottle(final EssenceBottle card) {
        super(card);
    }

    @Override
    public EssenceBottle copy() {
        return new EssenceBottle(this);
    }
}

class EssenceBottleCost extends CostImpl<EssenceBottleCost> {

    private int removedCounters;

    public EssenceBottleCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all elixir counters from {this}";
    }

    public EssenceBottleCost(EssenceBottleCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters().getCount(CounterType.ELIXIR);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.ELIXIR.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public EssenceBottleCost copy() {
        return new EssenceBottleCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
    }
}

class EssenceBottleEffect extends OneShotEffect<EssenceBottleEffect> {

    public EssenceBottleEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain 2 life for each elixir counter removed this way";
    }

    public EssenceBottleEffect(final EssenceBottleEffect effect) {
        super(effect);
    }

    @Override
    public EssenceBottleEffect copy() {
        return new EssenceBottleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof EssenceBottleCost) {
                countersRemoved = ((EssenceBottleCost) cost).getRemovedCounters();
            }
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(countersRemoved * 2, game);
            return true;
        }
        return false;
    }
}
