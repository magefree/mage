

package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class TreeOfTales extends CardImpl {

    public TreeOfTales (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.LAND},null);
        this.addAbility(new GreenManaAbility());
    }

    private TreeOfTales(final TreeOfTales card) {
        super(card);
    }

    @Override
    public TreeOfTales copy() {
        return new TreeOfTales(this);
    }

}
