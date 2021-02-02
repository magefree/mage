
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CarnivorousPlant extends CardImpl {

    public CarnivorousPlant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
    }

    private CarnivorousPlant(final CarnivorousPlant card) {
        super(card);
    }

    @Override
    public CarnivorousPlant copy() {
        return new CarnivorousPlant(this);
    }
}
