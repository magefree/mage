package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class ScannerSweep extends CardImpl {

    public ScannerSweep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");
        

        // Draw a card, then discard a card, then draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public ScannerSweep(final ScannerSweep card) {
        super(card);
    }

    @Override
    public ScannerSweep copy() {
        return new ScannerSweep(this);
    }
}
