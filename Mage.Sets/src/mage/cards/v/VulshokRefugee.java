
package mage.cards.v;

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
 * @author North
 */
public final class VulshokRefugee extends CardImpl {

    public VulshokRefugee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private VulshokRefugee(final VulshokRefugee card) {
        super(card);
    }

    @Override
    public VulshokRefugee copy() {
        return new VulshokRefugee(this);
    }
}
