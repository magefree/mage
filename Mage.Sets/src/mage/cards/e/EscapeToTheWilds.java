package mage.cards.e;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.*;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EscapeToTheWilds extends CardImpl {

    public EscapeToTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{G}");

        // Exile the top five cards of your library. You may play cards exiled this way until the end of your next turn.
        // You may play an additional land this turn.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(5, Duration.UntilEndOfYourNextTurn)
                .withTextOptions("cards exiled this way", true));
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn).concatBy("<br>"));
    }

    private EscapeToTheWilds(final EscapeToTheWilds card) {
        super(card);
    }

    @Override
    public EscapeToTheWilds copy() {
        return new EscapeToTheWilds(this);
    }
}

