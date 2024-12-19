package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SkyknightSquire extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 3);

    public SkyknightSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_ANOTHER_CREATURE, false));

        // As long as this creature has three or more +1/+1 counters on it, it has flying and is a Knight in addition to its other types.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "as long as this creature has " +
                "three or more +1/+1 counters on it, it has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, true, SubType.KNIGHT),
                condition, "and is a Knight in addition to its other types"
        ));
        this.addAbility(ability);
    }

    private SkyknightSquire(final SkyknightSquire card) {
        super(card);
    }

    @Override
    public SkyknightSquire copy() {
        return new SkyknightSquire(this);
    }
}
