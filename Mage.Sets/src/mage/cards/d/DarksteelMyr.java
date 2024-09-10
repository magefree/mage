

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DarksteelMyr extends CardImpl {

    public DarksteelMyr (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private DarksteelMyr(final DarksteelMyr card) {
        super(card);
    }

    @Override
    public DarksteelMyr copy() {
        return new DarksteelMyr(this);
    }

}
