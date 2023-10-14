package mage.cards.d;

import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DuelForDominance extends CardImpl {

    public DuelForDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Coven — Choose target creature you control and target creature you don't control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        // If you control three or more creatures with different powers, put a +1/+1 counter on the chosen creature you control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                CovenCondition.instance,
                "Choose target creature you control and target creature you don't control. " +
                        "If you control three or more creatures with different powers, put a +1/+1 counter on the chosen creature you control."
        ));
        // Then the chosen creatures fight each other.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Then the chosen creatures fight each other. <i>(They each deal damage equal to their power to the other.)</i>")
        );
        this.getSpellAbility().setAbilityWord(AbilityWord.COVEN);
        this.getSpellAbility().addHint(CovenHint.instance);
    }

    private DuelForDominance(final DuelForDominance card) {
        super(card);
    }

    @Override
    public DuelForDominance copy() {
        return new DuelForDominance(this);
    }
}
