

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ExultantSkymarcher extends CardImpl {

    public ExultantSkymarcher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ExultantSkymarcher(final ExultantSkymarcher card) {
        super(card);
    }

    @Override
    public ExultantSkymarcher copy() {
        return new ExultantSkymarcher(this);
    }

}
