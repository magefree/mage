package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ReplicatedRingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReplicatingRing extends CardImpl {

    public ReplicatingRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.SNOW);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // At the beginning of your upkeep, put a night counter on Replicating Ring. Then if it has eight or more night counters on it, remove all of them and create eight colorless snow artifact tokens named Replicated Ring with "{T}: Add one mana of any color."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ReplicatingRingEffect(), TargetController.YOU, false
        ));
    }

    private ReplicatingRing(final ReplicatingRing card) {
        super(card);
    }

    @Override
    public ReplicatingRing copy() {
        return new ReplicatingRing(this);
    }
}

class ReplicatingRingEffect extends OneShotEffect {

    ReplicatingRingEffect() {
        super(Outcome.Benefit);
        staticText = "put a night counter on {this}. Then if it has eight or more night counters on it, " +
                "remove all of them and create eight colorless snow artifact tokens named " +
                "Replicated Ring with \"{T}: Add one mana of any color.\"";
    }

    private ReplicatingRingEffect(final ReplicatingRingEffect effect) {
        super(effect);
    }

    @Override
    public ReplicatingRingEffect copy() {
        return new ReplicatingRingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.NIGHT.createInstance(), source.getControllerId(), source, game);
        }
        permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.NIGHT) < 8) {
            return true;
        }
        permanent.removeCounters(CounterType.NIGHT.createInstance(
                permanent.getCounters(game).getCount(CounterType.NIGHT)
        ),source,game);
        new ReplicatedRingToken().putOntoBattlefield(8, game, source, source.getControllerId());
        return true;
    }
}
