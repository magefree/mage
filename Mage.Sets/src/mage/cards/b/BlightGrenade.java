package mage.cards.b;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightGrenade extends CardImpl {

    public BlightGrenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // All creatures get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -3, -3, Duration.EndOfTurn
        ).concatBy("<br>"));
    }

    private BlightGrenade(final BlightGrenade card) {
        super(card);
    }

    @Override
    public BlightGrenade copy() {
        return new BlightGrenade(this);
    }
}
