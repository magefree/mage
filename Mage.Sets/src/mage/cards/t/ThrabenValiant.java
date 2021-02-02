
package mage.cards.t;

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
public final class ThrabenValiant extends CardImpl {

    public ThrabenValiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private ThrabenValiant(final ThrabenValiant card) {
        super(card);
    }

    @Override
    public ThrabenValiant copy() {
        return new ThrabenValiant(this);
    }
}
