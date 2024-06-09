

package mage.cards.s;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class SeatOfTheSynod extends CardImpl {

    public SeatOfTheSynod (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.LAND},null);
        this.addAbility(new BlueManaAbility());
    }

    private SeatOfTheSynod(final SeatOfTheSynod card) {
        super(card);
    }

    @Override
    public SeatOfTheSynod copy() {
        return new SeatOfTheSynod(this);
    }

}
