
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ShanodinDryads extends CardImpl {

    public ShanodinDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.NYMPH);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new ForestwalkAbility());
    }

    private ShanodinDryads(final ShanodinDryads card) {
        super(card);
    }

    @Override
    public ShanodinDryads copy() {
        return new ShanodinDryads(this);
    }
}
