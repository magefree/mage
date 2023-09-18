

package mage.cards.a;

import java.util.UUID;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class AncientDen extends CardImpl {

    public AncientDen (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.LAND},null);
        this.addAbility(new WhiteManaAbility());
    }

    private AncientDen(final AncientDen card) {
        super(card);
    }

    @Override
    public AncientDen copy() {
        return new AncientDen(this);
    }

}
