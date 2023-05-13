
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author L_J
 */
public final class ZetalpaPrimalDawn extends CardImpl {

    public ZetalpaPrimalDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private ZetalpaPrimalDawn(final ZetalpaPrimalDawn card) {
        super(card);
    }

    @Override
    public ZetalpaPrimalDawn copy() {
        return new ZetalpaPrimalDawn(this);
    }
}
