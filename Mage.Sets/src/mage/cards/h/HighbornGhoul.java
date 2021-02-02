
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HighbornGhoul extends CardImpl {

    public HighbornGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(IntimidateAbility.getInstance());
    }

    private HighbornGhoul(final HighbornGhoul card) {
        super(card);
    }

    @Override
    public HighbornGhoul copy() {
        return new HighbornGhoul(this);
    }
}
