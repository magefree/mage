

package mage.cards.g;

import java.util.UUID;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class GreatFurnace extends CardImpl {

    public GreatFurnace (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.LAND},null);
        this.addAbility(new RedManaAbility());
    }

    private GreatFurnace(final GreatFurnace card) {
        super(card);
    }

    @Override
    public GreatFurnace copy() {
        return new GreatFurnace(this);
    }

}
