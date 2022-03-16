package mage.cards.a;

import java.util.UUID;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

/**
 *
 * @author weirddan455
 */
public final class AmbitiousAssault extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("you control a modified creature");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public AmbitiousAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Creatures you control get +2/+0 until end of turn. If you control a modified creature, draw a card.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), condition));
        this.getSpellAbility().addHint(hint);
    }

    private AmbitiousAssault(final AmbitiousAssault card) {
        super(card);
    }

    @Override
    public AmbitiousAssault copy() {
        return new AmbitiousAssault(this);
    }
}
