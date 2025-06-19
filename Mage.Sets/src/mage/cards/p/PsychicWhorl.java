package mage.cards.p;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsychicWhorl extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.RAT));
    private static final Hint hint = new ConditionHint(condition, "You control a Rat");

    public PsychicWhorl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent discards two cards. Then if you control a Rat, surveil 2.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SurveilEffect(2), condition,
                "Then if you control a Rat, surveil 2"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private PsychicWhorl(final PsychicWhorl card) {
        super(card);
    }

    @Override
    public PsychicWhorl copy() {
        return new PsychicWhorl(this);
    }
}
