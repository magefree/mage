
package mage.cards.z;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class ZoeticCavern extends CardImpl {

    public ZoeticCavern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());        
        // Morph {2}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}")));
    }

    private ZoeticCavern(final ZoeticCavern card) {
        super(card);
    }

    @Override
    public ZoeticCavern copy() {
        return new ZoeticCavern(this);
    }
}
