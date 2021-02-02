
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RakdosRagemutt extends CardImpl {

    public RakdosRagemutt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private RakdosRagemutt(final RakdosRagemutt card) {
        super(card);
    }

    @Override
    public RakdosRagemutt copy() {
        return new RakdosRagemutt(this);
    }
}
