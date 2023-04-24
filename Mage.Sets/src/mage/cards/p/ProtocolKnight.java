package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProtocolKnight extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control another Knight");

    public ProtocolKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Protocol Knight enters the battlefield, tap target creature an opponent controls. Put a stun counter on that creature if you control another Knight.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.STUN.createInstance()), condition,
                "put a stun counter on that creature if you control another Knight"
        ));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(hint));
    }

    private ProtocolKnight(final ProtocolKnight card) {
        super(card);
    }

    @Override
    public ProtocolKnight copy() {
        return new ProtocolKnight(this);
    }
}
