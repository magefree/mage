package mage.cards.p;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PryingEyes extends CardImpl {

    public PryingEyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");


        // Draw four cards, then discard two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        this.getSpellAbility().addEffect(new DiscardControllerEffect(2).concatBy(", then"));
    }

    private PryingEyes(final PryingEyes card) {
        super(card);
    }

    @Override
    public PryingEyes copy() {
        return new PryingEyes(this);
    }
}
