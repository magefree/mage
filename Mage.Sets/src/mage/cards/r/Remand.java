package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class Remand extends CardImpl {

    public Remand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell. If that spell is countered this way, put it into its owner's hand instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.HAND));
        this.getSpellAbility().addTarget(new TargetSpell());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Remand(final Remand card) {
        super(card);
    }

    @Override
    public Remand copy() {
        return new Remand(this);
    }
}
