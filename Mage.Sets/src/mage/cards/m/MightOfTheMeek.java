package mage.cards.m;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MightOfTheMeek extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MOUSE, "you control a Mouse");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public MightOfTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gains trample until end of turn. It also gets +1/+0 until end of turn if you control a Mouse.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(1, 0)),
                condition, "It also gets +1/+0 until end of turn if you control a Mouse"
        ));
        this.getSpellAbility().addHint(hint);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private MightOfTheMeek(final MightOfTheMeek card) {
        super(card);
    }

    @Override
    public MightOfTheMeek copy() {
        return new MightOfTheMeek(this);
    }
}
