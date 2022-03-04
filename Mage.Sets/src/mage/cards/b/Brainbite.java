
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class Brainbite extends CardImpl {

    public Brainbite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Target opponent reveals their hand. You choose a card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Brainbite(final Brainbite card) {
        super(card);
    }

    @Override
    public Brainbite copy() {
        return new Brainbite(this);
    }
}
