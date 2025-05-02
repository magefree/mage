package mage.cards.s;

import mage.abilities.condition.common.CastAnotherSpellThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.*;

/**
 * @author Susucr
 */
public final class SlickSequence extends CardImpl {

    public SlickSequence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Slick Sequence deals 2 damage to any target. If you've cast another spell this turn, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1),
                        CastAnotherSpellThisTurnCondition.instance
                )
        );
        this.getSpellAbility().addHint(CastAnotherSpellThisTurnCondition.instance.getHint());
    }

    private SlickSequence(final SlickSequence card) {
        super(card);
    }

    @Override
    public SlickSequence copy() {
        return new SlickSequence(this);
    }
}