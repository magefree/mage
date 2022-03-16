package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronApprentice extends CardImpl {

    public IronApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Iron Apprentice enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(1)
        ), "with a +1/+1 counter on it"));

        // When Iron Apprentice dies, if it had counters on it, put those counters on target creature you control.
        Ability ability = new ConditionalTriggeredAbility(
                new DiesSourceTriggeredAbility(new IronApprenticeEffect()), IronApprenticeCondition.instance,
                "When {this} dies, if it had counters on it, put those counters on target creature you control."
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private IronApprentice(final IronApprentice card) {
        super(card);
    }

    @Override
    public IronApprentice copy() {
        return new IronApprentice(this);
    }
}

enum IronApprenticeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .anyMatch(x -> x > 0);
    }
}

class IronApprenticeEffect extends OneShotEffect {

    IronApprenticeEffect() {
        super(Outcome.Benefit);
    }

    private IronApprenticeEffect(final IronApprenticeEffect effect) {
        super(effect);
    }

    @Override
    public IronApprenticeEffect copy() {
        return new IronApprenticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (permanent == null || creature == null) {
            return false;
        }
        permanent
                .getCounters(game)
                .copy()
                .values()
                .stream()
                .forEach(counter -> creature.addCounters(counter, source, game));
        return true;
    }
}
