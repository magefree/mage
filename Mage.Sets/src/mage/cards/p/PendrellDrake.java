
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PendrellDrake extends CardImpl {

    public PendrellDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private PendrellDrake(final PendrellDrake card) {
        super(card);
    }

    @Override
    public PendrellDrake copy() {
        return new PendrellDrake(this);
    }
}
