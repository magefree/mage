

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Skylasher extends CardImpl {

    public Skylasher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Skylasher can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // Reach, protection from blue
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));

    }

    private Skylasher(final Skylasher card) {
        super(card);
    }

    @Override
    public Skylasher copy() {
        return new Skylasher(this);
    }

}
