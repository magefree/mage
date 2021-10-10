package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureMap extends CardImpl {

    public TreasureMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.transformable = true;
        this.secondSideCardClazz = TreasureCove.class;

        // {1}, {T}: Scry 1. Put a landmark counter on Treasure Map. Then if there are three or more landmark counters on it, remove those counters, transform Treasure Map, and create three colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TreasureMapEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TreasureMap(final TreasureMap card) {
        super(card);
    }

    @Override
    public TreasureMap copy() {
        return new TreasureMap(this);
    }
}

class TreasureMapEffect extends OneShotEffect {

    TreasureMapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Scry 1. Put a landmark counter on {this}. "
                + "Then if there are three or more landmark counters on it, "
                + "remove those counters, transform {this}, and create "
                + "three Treasure tokens";
    }

    TreasureMapEffect(final TreasureMapEffect effect) {
        super(effect);
    }

    @Override
    public TreasureMapEffect copy() {
        return new TreasureMapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.scry(1, source, game);
            if (permanent != null) {
                permanent.addCounters(CounterType.LANDMARK.createInstance(), source.getControllerId(), source, game);
                int counters = permanent.getCounters(game).getCount(CounterType.LANDMARK);
                if (counters > 2) {
                    permanent.removeCounters("landmark", counters, source, game);
                    new TransformSourceEffect(true).apply(game, source);
                    new CreateTokenEffect(new TreasureToken(), 3).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
