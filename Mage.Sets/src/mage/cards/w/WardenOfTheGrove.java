package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WardenOfTheGrove extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public WardenOfTheGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Whenever another nontoken creature you control enters, it endures X, where X is the number of counters on this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new WardenOfTheGroveEffect(),
                filter, false, SetTargetPointer.PERMANENT
        ));
    }

    private WardenOfTheGrove(final WardenOfTheGrove card) {
        super(card);
    }

    @Override
    public WardenOfTheGrove copy() {
        return new WardenOfTheGrove(this);
    }
}

class WardenOfTheGroveEffect extends OneShotEffect {

    WardenOfTheGroveEffect() {
        super(Outcome.Benefit);
        staticText = "it endures X, where X is the number of counters on {this}";
    }

    private WardenOfTheGroveEffect(final WardenOfTheGroveEffect effect) {
        super(effect);
    }

    @Override
    public WardenOfTheGroveEffect copy() {
        return new WardenOfTheGroveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return EndureSourceEffect.doEndure(
                this.getTargetPointer().getFirstTargetPermanentOrLKI(game, source),
                Optional.ofNullable(source.getSourcePermanentOrLKI(game))
                        .map(p -> p.getCounters(game))
                        .map(Counters::getTotalCount)
                        .orElse(0),
                game, source
        );
    }
}
