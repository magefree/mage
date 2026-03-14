package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MastersGuidance extends CardImpl {

    public MastersGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack with two or more legendary creatures, put a +1/+1 counter on each of up to two target attacking creatures.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                2, StaticFilters.FILTER_CREATURES_LEGENDARY
        );
        ability.addTarget(new TargetAttackingCreature(0, 2));
        this.addAbility(ability);

        // At the beginning of your end step, if you control a creature with power 4 or greater, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(FerociousCondition.instance)
                .addHint(FerociousHint.instance));
    }

    private MastersGuidance(final MastersGuidance card) {
        super(card);
    }

    @Override
    public MastersGuidance copy() {
        return new MastersGuidance(this);
    }
}
