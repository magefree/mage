package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author awjackson
 */
public final class AgonyWarp extends CardImpl {

    public AgonyWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}");

        // Target creature gets -3/-0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("-3/-0"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, 0, Duration.EndOfTurn));

        // Target creature gets -0/-3 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("-0/-3"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, -3, Duration.EndOfTurn)
                .setTargetPointer(new SecondTargetPointer())
                .concatBy("<br>"));
    }

    private AgonyWarp(final AgonyWarp card) {
        super(card);
    }

    @Override
    public AgonyWarp copy() {
        return new AgonyWarp(this);
    }
}
