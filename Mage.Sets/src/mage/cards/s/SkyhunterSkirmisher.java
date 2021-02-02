
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SkyhunterSkirmisher extends CardImpl {

    public SkyhunterSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private SkyhunterSkirmisher(final SkyhunterSkirmisher card) {
        super(card);
    }

    @Override
    public SkyhunterSkirmisher copy() {
        return new SkyhunterSkirmisher(this);
    }
}
