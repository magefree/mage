package mage.cards.a;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class ActOnImpulse extends CardImpl {

    public ActOnImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top three cards of your library. Until end of turn, you may play cards exiled this way.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(3));
    }

    private ActOnImpulse(final ActOnImpulse card) {
        super(card);
    }

    @Override
    public ActOnImpulse copy() {
        return new ActOnImpulse(this);
    }
}

