package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GrakmawSkyclaveRavagerHydraToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrakmawSkyclaveRavager extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CounterType.P1P1.getPredicate());
    }

    public GrakmawSkyclaveRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Grakmaw, Skyclave Ravager enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                "with three +1/+1 counters on it"
        ));

        // Whenever another creature you control dies, if it had a +1/+1 counter on it, put a +1/+1 counter on Grakmaw.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("if it had a +1/+1 counter on it, put a +1/+1 counter on {this}"),
                false, filter
        ));

        // When Grakmaw dies, create an X/X black and green Hydra creature token, where X is the number of +1/+1 counters on Grakmaw.
        this.addAbility(new DiesSourceTriggeredAbility(new GrakmawSkyclaveRavagerEffect()));
    }

    private GrakmawSkyclaveRavager(final GrakmawSkyclaveRavager card) {
        super(card);
    }

    @Override
    public GrakmawSkyclaveRavager copy() {
        return new GrakmawSkyclaveRavager(this);
    }
}

class GrakmawSkyclaveRavagerEffect extends OneShotEffect {

    GrakmawSkyclaveRavagerEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X black and green Hydra creature token, " +
                "where X is the number of +1/+1 counters on {this}";
    }

    private GrakmawSkyclaveRavagerEffect(final GrakmawSkyclaveRavagerEffect effect) {
        super(effect);
    }

    @Override
    public GrakmawSkyclaveRavagerEffect copy() {
        return new GrakmawSkyclaveRavagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counters = 0;
        Permanent permanent = (Permanent) getValue("permanentLeftBattlefield");
        if (permanent != null) {
            counters = permanent.getCounters(game).getCount(CounterType.P1P1);
        }
        return new GrakmawSkyclaveRavagerHydraToken(counters)
                .putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
