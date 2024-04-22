package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaylayingPirates extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);
    private static final Hint hint = new ConditionHint(condition, "You control an artifact");

    public WaylayingPirates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Waylaying Pirates enters the battlefield, if you control an artifact, tap target artifact or creature an opponent controls and put a stun counter on it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TapTargetEffect()),
                condition, "When {this} enters the battlefield, if you control an artifact, " +
                "tap target artifact or creature an opponent controls and put a stun counter on it."
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability.addHint(hint));
    }

    private WaylayingPirates(final WaylayingPirates card) {
        super(card);
    }

    @Override
    public WaylayingPirates copy() {
        return new WaylayingPirates(this);
    }
}
