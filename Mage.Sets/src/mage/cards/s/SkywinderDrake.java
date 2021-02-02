                        
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SkywinderDrake extends CardImpl {

    public SkywinderDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // Skywinder Drake can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private SkywinderDrake(final SkywinderDrake card) {
        super(card);
    }

    @Override
    public SkywinderDrake copy() {
        return new SkywinderDrake(this);
    }
}
