
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class MarhaultElsdragon extends CardImpl {

    public MarhaultElsdragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Rampage 1
        this.addAbility(new RampageAbility(1));
    }

    private MarhaultElsdragon(final MarhaultElsdragon card) {
        super(card);
    }

    @Override
    public MarhaultElsdragon copy() {
        return new MarhaultElsdragon(this);
    }
}
