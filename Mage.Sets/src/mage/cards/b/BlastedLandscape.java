

package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class BlastedLandscape extends CardImpl {

    public BlastedLandscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private BlastedLandscape(final BlastedLandscape card) {
        super(card);
    }

    @Override
    public BlastedLandscape copy() {
        return new BlastedLandscape(this);
    }
}
