
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
 * @author noxx

 */
public final class ScrapskinDrake extends CardImpl {

    public ScrapskinDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Scrapskin Drake can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private ScrapskinDrake(final ScrapskinDrake card) {
        super(card);
    }

    @Override
    public ScrapskinDrake copy() {
        return new ScrapskinDrake(this);
    }
}
