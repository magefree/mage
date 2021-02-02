
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author LevelX2
 */
public final class Thoughtflare extends CardImpl {

    public Thoughtflare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{R}");


        // Draw four cards, then discard two cards.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(4, 2));
    }

    private Thoughtflare(final Thoughtflare card) {
        super(card);
    }

    @Override
    public Thoughtflare copy() {
        return new Thoughtflare(this);
    }
}

