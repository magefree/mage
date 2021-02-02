

package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ZombieOutlander extends CardImpl {

    public ZombieOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
    }

    private ZombieOutlander(final ZombieOutlander card) {
        super(card);
    }

    @Override
    public ZombieOutlander copy() {
        return new ZombieOutlander(this);
    }

}
