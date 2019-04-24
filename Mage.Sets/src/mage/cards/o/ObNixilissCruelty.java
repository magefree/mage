package mage.cards.o;

import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObNixilissCruelty extends CardImpl {

    public ObNixilissCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target creature gets -5/-5 until end of turn. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ObNixilissCruelty(final ObNixilissCruelty card) {
        super(card);
    }

    @Override
    public ObNixilissCruelty copy() {
        return new ObNixilissCruelty(this);
    }
}
