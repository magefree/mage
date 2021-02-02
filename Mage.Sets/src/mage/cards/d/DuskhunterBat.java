
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DuskhunterBat extends CardImpl {

    public DuskhunterBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.BAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new BloodthirstAbility(1));
        this.addAbility(FlyingAbility.getInstance());
    }

    private DuskhunterBat(final DuskhunterBat card) {
        super(card);
    }

    @Override
    public DuskhunterBat copy() {
        return new DuskhunterBat(this);
    }
}
