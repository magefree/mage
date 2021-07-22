
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author nantuko
 */
public final class Ponder extends CardImpl {

    public Ponder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Look at the top three cards of your library, then put them back in any order. You may shuffle your library.
        this.getSpellAbility().addEffect(new LookLibraryControllerEffect(3, true, true));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Ponder(final Ponder card) {
        super(card);
    }

    @Override
    public Ponder copy() {
        return new Ponder(this);
    }
}