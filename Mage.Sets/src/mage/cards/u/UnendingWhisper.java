package mage.cards.u;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnendingWhisper extends CardImpl {

    public UnendingWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Harmonize {5}{U}
        this.addAbility(new HarmonizeAbility(this, "{5}{U}"));
    }

    private UnendingWhisper(final UnendingWhisper card) {
        super(card);
    }

    @Override
    public UnendingWhisper copy() {
        return new UnendingWhisper(this);
    }
}
