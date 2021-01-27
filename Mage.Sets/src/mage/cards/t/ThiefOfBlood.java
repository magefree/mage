package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ThiefOfBlood extends CardImpl {

    public ThiefOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.subtype.add(SubType.VAMPIRE);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Thief of Blood enters the battlefield, remove all counters from all permanents. Thief of Blood enters the battlefield with a +1/+1 counter on it for each counter removed this way.
        this.addAbility(new EntersBattlefieldAbility(new ThiefOfBloodEffect(), null, "As {this} enters the battlefield, remove all counters from all permanents. {this} enters the battlefield with a +1/+1 counter on it for each counter removed this way", null));
    }

    private ThiefOfBlood(final ThiefOfBlood card) {
        super(card);
    }

    @Override
    public ThiefOfBlood copy() {
        return new ThiefOfBlood(this);
    }
}

class ThiefOfBloodEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a counter");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    ThiefOfBloodEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "remove all counters from all permanents. {this} enters the battlefield with a +1/+1 counter on it for each counter removed this way";
    }

    private ThiefOfBloodEffect(final ThiefOfBloodEffect effect) {
        super(effect);
    }

    @Override
    public ThiefOfBloodEffect copy() {
        return new ThiefOfBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            Counters counters = permanent.getCounters(game).copy();
            for (Counter counter : counters.values()) {
                permanent.removeCounters(counter.getName(), counter.getCount(), source, game);
                countersRemoved += counter.getCount();
            }
        }
        if (countersRemoved > 0) {
            Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
            if (sourcePermanent != null) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(countersRemoved), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
