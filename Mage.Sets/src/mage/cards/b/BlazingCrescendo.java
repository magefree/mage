package mage.cards.b;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
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
public final class BlazingCrescendo extends CardImpl {

    public BlazingCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Exile the top card of your library. Until the end of your next turn, you may play that card.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(
                1, false, Duration.UntilEndOfYourNextTurn
        ));
    }

    private BlazingCrescendo(final BlazingCrescendo card) {
        super(card);
    }

    @Override
    public BlazingCrescendo copy() {
        return new BlazingCrescendo(this);
    }
}
