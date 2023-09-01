

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SnappingDrake extends CardImpl {

    public SnappingDrake (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private SnappingDrake(final SnappingDrake card) {
        super(card);
    }

    @Override
    public SnappingDrake copy() {
        return new SnappingDrake(this);
    }

}
