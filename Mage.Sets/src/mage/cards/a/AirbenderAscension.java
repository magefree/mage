package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirbenderAscension extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, ComparisonType.MORE_THAN, 3);

    public AirbenderAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // When this enchantment enters, airbend up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever a creature you control enters, put a quest counter on this enchantment.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // At the beginning of your end step, if this enchantment has four or more quest counters on it, exile up to one target creature you control, then return it to the battlefield under its owner's control.
        ability = new BeginningOfEndStepTriggeredAbility(
                new ExileThenReturnTargetEffect(false, false)
        ).withInterveningIf(condition);
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private AirbenderAscension(final AirbenderAscension card) {
        super(card);
    }

    @Override
    public AirbenderAscension copy() {
        return new AirbenderAscension(this);
    }
}
