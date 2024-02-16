package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SternLesson extends CardImpl {

    public SternLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw two cards, then discard a card. Create a tapped Powerstone token.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true));
    }

    private SternLesson(final SternLesson card) {
        super(card);
    }

    @Override
    public SternLesson copy() {
        return new SternLesson(this);
    }
}
