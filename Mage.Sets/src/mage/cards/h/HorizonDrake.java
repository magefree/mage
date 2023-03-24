package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class HorizonDrake extends CardImpl {

    public HorizonDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ProtectionAbility.from(CardType.LAND));
    }

    private HorizonDrake(final HorizonDrake card) {
        super(card);
    }

    @Override
    public HorizonDrake copy() {
        return new HorizonDrake(this);
    }

}
