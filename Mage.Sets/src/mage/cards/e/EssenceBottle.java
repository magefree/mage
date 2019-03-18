
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class EssenceBottle extends CardImpl {

    public EssenceBottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
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

class EssenceBottleCost extends CostImpl {

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
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters(game).getCount(CounterType.ELIXIR);
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

class EssenceBottleEffect extends OneShotEffect {

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
            player.gainLife(countersRemoved * 2, game, source);
            return true;
        }
        return false;
    }
}
