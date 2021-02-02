
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class WiltLeafCavaliers extends CardImpl {

    public WiltLeafCavaliers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/W}{G/W}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private WiltLeafCavaliers(final WiltLeafCavaliers card) {
        super(card);
    }

    @Override
    public WiltLeafCavaliers copy() {
        return new WiltLeafCavaliers(this);
    }
}
