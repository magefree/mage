package mage.cards.c;

import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.BargainCostWasPaidHint;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CandyGrapple extends CardImpl {

    public CandyGrapple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Bargain
        this.addAbility(new BargainAbility());

        // Target creature gets -3/-3 until end of turn. If this spell was bargained, that creature gets -5/-5 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-5, -5)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-3, -3)),
                BargainedCondition.instance,
                "Target creature gets -3/-3 until end of turn. If this spell was bargained, "
                        + "that creature gets -5/-5 until end of turn instead."
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(BargainCostWasPaidHint.instance);
    }

    private CandyGrapple(final CandyGrapple card) {
        super(card);
    }

    @Override
    public CandyGrapple copy() {
        return new CandyGrapple(this);
    }
}