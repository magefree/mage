package mage.cards.a;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class ActOnImpulse extends CardImpl {

    public ActOnImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top three cards of your library. Until end of turn, you may play cards exiled this way.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(3, Duration.EndOfTurn)
                .withTextOptions("cards exiled this way. <i>(If you cast a spell this way, you still pay its costs. " +
                        "You can play a land this way only if you have an available land play remaining.)</i>", false));
    }

    private ActOnImpulse(final ActOnImpulse card) {
        super(card);
    }

    @Override
    public ActOnImpulse copy() {
        return new ActOnImpulse(this);
    }
}
