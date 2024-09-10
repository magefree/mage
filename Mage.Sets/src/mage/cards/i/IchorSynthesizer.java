package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IchorSynthesizer extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.OIL, 4);

    public IchorSynthesizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a noncreature spell, put an oil counter on Ichor Synthesizer.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // As long as Ichor Synthesizer has four or more oil counters on it, it gets +2/+0 and can't be blocked.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                condition, "as long as {this} has four or more oil counters on it, it gets +2/+0"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), condition, "and can't be blocked"
        ));
        this.addAbility(ability);
    }

    private IchorSynthesizer(final IchorSynthesizer card) {
        super(card);
    }

    @Override
    public IchorSynthesizer copy() {
        return new IchorSynthesizer(this);
    }
}
