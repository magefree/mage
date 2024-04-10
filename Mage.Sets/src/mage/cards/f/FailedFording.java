package mage.cards.f;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FailedFording extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.DESERT));
    private static final Hint hint = new ConditionHint(condition, "You control a Desert");

    public FailedFording(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. If you control a Desert, surveil 1.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SurveilEffect(1), condition,
                "if you control a Desert, surveil 1"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private FailedFording(final FailedFording card) {
        super(card);
    }

    @Override
    public FailedFording copy() {
        return new FailedFording(this);
    }
}
