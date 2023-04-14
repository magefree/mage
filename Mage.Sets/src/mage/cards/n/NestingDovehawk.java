package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NestingDovehawk extends CardImpl {

    public NestingDovehawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, populate.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new PopulateEffect(), TargetController.YOU, false));

        // Whenever a creature token enters the battlefield under your control, put a +1/+1 counter on Nesting Dovehawk.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_CREATURE_TOKEN
        ));
    }

    private NestingDovehawk(final NestingDovehawk card) {
        super(card);
    }

    @Override
    public NestingDovehawk copy() {
        return new NestingDovehawk(this);
    }
}
