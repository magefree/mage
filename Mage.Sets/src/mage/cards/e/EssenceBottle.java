package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class EssenceBottle extends CardImpl {

    public EssenceBottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {tap}: Put an elixir counter on Essence Bottle.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.ELIXIR.createInstance()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Remove all elixir counters from Essence Bottle: You gain 2 life for each elixir counter removed this way.
        ability = new SimpleActivatedAbility(new EssenceBottleEffect(), new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.ELIXIR));
        this.addAbility(ability);
    }

    private EssenceBottle(final EssenceBottle card) {
        super(card);
    }

    @Override
    public EssenceBottle copy() {
        return new EssenceBottle(this);
    }
}

class EssenceBottleEffect extends OneShotEffect {

    public EssenceBottleEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain 2 life for each elixir counter removed this way";
    }

    private EssenceBottleEffect(final EssenceBottleEffect effect) {
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
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
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
