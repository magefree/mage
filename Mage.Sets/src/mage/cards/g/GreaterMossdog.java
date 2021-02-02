
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class GreaterMossdog extends CardImpl {

    public GreaterMossdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dredge 3
        this.addAbility(new DredgeAbility(3));
    }

    private GreaterMossdog(final GreaterMossdog card) {
        super(card);
    }

    @Override
    public GreaterMossdog copy() {
        return new GreaterMossdog(this);
    }
}
