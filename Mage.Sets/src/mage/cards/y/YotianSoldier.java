
package mage.cards.y;

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
public final class YotianSoldier extends CardImpl {

    public YotianSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private YotianSoldier(final YotianSoldier card) {
        super(card);
    }

    @Override
    public YotianSoldier copy() {
        return new YotianSoldier(this);
    }
}
