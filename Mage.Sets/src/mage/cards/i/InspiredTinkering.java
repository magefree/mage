package mage.cards.i;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiredTinkering extends CardImpl {

    public InspiredTinkering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Exile the top three cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(
                3, false, Duration.UntilEndOfYourNextTurn
        ));

        // Create three Treasure tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 3).concatBy("<br>"));
    }

    private InspiredTinkering(final InspiredTinkering card) {
        super(card);
    }

    @Override
    public InspiredTinkering copy() {
        return new InspiredTinkering(this);
    }
}
