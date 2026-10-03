package mage.cards.g;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class Gardenize extends CardImpl {

    public Gardenize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a creature you control dies, put a charge counter on this enchantment.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
            false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // At the beginning of your first main phase, add {G} for each charge counter on this enchantment.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new GardenizeEffect()));
    }

    private Gardenize(final Gardenize card) {
        super(card);
    }

    @Override
    public Gardenize copy() {
        return new Gardenize(this);
    }
}

class GardenizeEffect extends OneShotEffect {

    GardenizeEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add {G} for each charge counter on {this}";
    }

    private GardenizeEffect(final GardenizeEffect effect) {
        super(effect);
    }

    @Override
    public GardenizeEffect copy() {
        return new GardenizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player == null || sourcePermanent == null) {
            return false;
        }

        int chargeCounters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
        if (chargeCounters > 0) {
            player.getManaPool().addMana(Mana.GreenMana(chargeCounters), game, source);
        }
        return true;
    }
}
