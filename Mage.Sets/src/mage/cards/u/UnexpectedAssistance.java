package mage.cards.u;

import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnexpectedAssistance extends CardImpl {

    public UnexpectedAssistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));
    }

    private UnexpectedAssistance(final UnexpectedAssistance card) {
        super(card);
    }

    @Override
    public UnexpectedAssistance copy() {
        return new UnexpectedAssistance(this);
    }
}
