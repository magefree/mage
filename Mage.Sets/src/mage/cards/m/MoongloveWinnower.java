
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class MoongloveWinnower extends CardImpl {

    public MoongloveWinnower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(DeathtouchAbility.getInstance());
    }

    private MoongloveWinnower(final MoongloveWinnower card) {
        super(card);
    }

    @Override
    public MoongloveWinnower copy() {
        return new MoongloveWinnower(this);
    }
}
