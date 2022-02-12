package mage.cards.s;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class StubbornDenial extends CardImpl {

    public StubbornDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target noncreature spell unless its controller pays {1}.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(1)),
                new InvertCondition(FerociousCondition.instance),
                "Counter target noncreature spell unless its controller pays {1}."));

        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, counter that spell instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(),
                FerociousCondition.instance,
                "<br><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, counter that spell instead"));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private StubbornDenial(final StubbornDenial card) {
        super(card);
    }

    @Override
    public StubbornDenial copy() {
        return new StubbornDenial(this);
    }
}
