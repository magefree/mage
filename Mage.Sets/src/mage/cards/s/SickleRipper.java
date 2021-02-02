
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SickleRipper extends CardImpl {

    public SickleRipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(WitherAbility.getInstance());
    }

    private SickleRipper(final SickleRipper card) {
        super(card);
    }

    @Override
    public SickleRipper copy() {
        return new SickleRipper(this);
    }
}
