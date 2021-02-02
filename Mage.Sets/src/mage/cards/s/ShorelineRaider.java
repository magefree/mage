
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox

 */
public final class ShorelineRaider extends CardImpl {

    private static final FilterCard filter = new FilterCard("Kavu");

    static {
        filter.add(SubType.KAVU.getPredicate());
    }


    public ShorelineRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from Kavu
        this.addAbility(new ProtectionAbility(filter));
    }

    private ShorelineRaider(final ShorelineRaider card) {
        super(card);
    }

    @Override
    public ShorelineRaider copy() {
        return new ShorelineRaider(this);
    }
}
