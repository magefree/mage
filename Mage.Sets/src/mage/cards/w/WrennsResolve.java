package mage.cards.w;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennsResolve extends CardImpl {

    public WrennsResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(2, false, Duration.UntilEndOfYourNextTurn));
    }

    private WrennsResolve(final WrennsResolve card) {
        super(card);
    }

    @Override
    public WrennsResolve copy() {
        return new WrennsResolve(this);
    }
}
