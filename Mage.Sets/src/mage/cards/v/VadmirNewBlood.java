package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VadmirNewBlood extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 4);

    public VadmirNewBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you commit a crime, put a +1/+1 counter on Vadmir, New Blood. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).setTriggersOnceEachTurn(true));

        // As long as Vadmir has four or more +1/+1 counters on it, it has menace and lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield
        ), condition, "As long as {this} has four or more +1/+1 counters on it, it has menace"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield
        ), condition, "and lifelink"));
        this.addAbility(ability);
    }

    private VadmirNewBlood(final VadmirNewBlood card) {
        super(card);
    }

    @Override
    public VadmirNewBlood copy() {
        return new VadmirNewBlood(this);
    }
}
