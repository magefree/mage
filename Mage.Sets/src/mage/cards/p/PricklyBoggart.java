
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PricklyBoggart extends CardImpl {

    public PricklyBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FearAbility.getInstance());
    }

    private PricklyBoggart(final PricklyBoggart card) {
        super(card);
    }

    @Override
    public PricklyBoggart copy() {
        return new PricklyBoggart(this);
    }
}
