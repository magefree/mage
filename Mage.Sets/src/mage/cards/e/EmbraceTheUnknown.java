package mage.cards.e;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EmbraceTheUnknown extends CardImpl {

    public EmbraceTheUnknown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(
                2, Duration.UntilEndOfYourNextTurn
        ));

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private EmbraceTheUnknown(final EmbraceTheUnknown card) {
        super(card);
    }

    @Override
    public EmbraceTheUnknown copy() {
        return new EmbraceTheUnknown(this);
    }
}
