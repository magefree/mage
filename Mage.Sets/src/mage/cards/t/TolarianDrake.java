
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author noxx
 */
public final class TolarianDrake extends CardImpl {

    public TolarianDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Phasing
        this.addAbility(PhasingAbility.getInstance());
    }

    private TolarianDrake(final TolarianDrake card) {
        super(card);
    }

    @Override
    public TolarianDrake copy() {
        return new TolarianDrake(this);
    }
}
