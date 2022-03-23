
package mage.cards.k;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class KalonianHydra extends CardImpl {

    public KalonianHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Kalonian Hydra enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4))));
        // Whenever Kalonian Hydra attacks, double the number of +1/+1 counters on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(new KalonianHydraEffect(), false));

    }

    private KalonianHydra(final KalonianHydra card) {
        super(card);
    }

    @Override
    public KalonianHydra copy() {
        return new KalonianHydra(this);
    }
}


class KalonianHydraEffect extends OneShotEffect {

    public KalonianHydraEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "double the number of +1/+1 counters on each creature you control";
    }

    public KalonianHydraEffect(final KalonianHydraEffect effect) {
        super(effect);
    }

    @Override
    public KalonianHydraEffect copy() {
        return new KalonianHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            int existingCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
            if (existingCounters > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(existingCounters), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
