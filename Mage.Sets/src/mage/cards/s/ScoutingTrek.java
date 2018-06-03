
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.RecruiterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterBasicLandCard;

/**
 *
 * @author LoneFox
 */
public final class ScoutingTrek extends CardImpl {

    public ScoutingTrek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Search your library for any number of basic land cards. Reveal those cards, then shuffle your library and put them on top of it.
        this.getSpellAbility().addEffect(new RecruiterEffect(new FilterBasicLandCard("basic land cards")));
    }

    public ScoutingTrek(final ScoutingTrek card) {
        super(card);
    }

    @Override
    public ScoutingTrek copy() {
        return new ScoutingTrek(this);
    }
}
