

package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author LevelX
 */
public final class Index extends CardImpl {

    public Index(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Look at the top five cards of your library, then put them back in any order.
        this.getSpellAbility().addEffect(new LookLibraryControllerEffect(5));
    }

    private Index(final Index card) {
        super(card);
    }

    @Override
    public Index copy() {
        return new Index(this);
    }

}
