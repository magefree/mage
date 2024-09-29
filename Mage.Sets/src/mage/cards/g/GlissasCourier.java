

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GlissasCourier extends CardImpl {

    public GlissasCourier (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new MountainwalkAbility());
    }

    private GlissasCourier(final GlissasCourier card) {
        super(card);
    }

    @Override
    public GlissasCourier copy() {
        return new GlissasCourier(this);
    }

}
