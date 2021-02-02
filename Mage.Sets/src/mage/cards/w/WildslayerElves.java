
package mage.cards.w;

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
public final class WildslayerElves extends CardImpl {

    public WildslayerElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(WitherAbility.getInstance());
    }

    private WildslayerElves(final WildslayerElves card) {
        super(card);
    }

    @Override
    public WildslayerElves copy() {
        return new WildslayerElves(this);
    }
}
