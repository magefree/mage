

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AccorderPaladin extends CardImpl {

    public AccorderPaladin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(new BattleCryAbility());
    }

    private AccorderPaladin(final AccorderPaladin card) {
        super(card);
    }

    @Override
    public AccorderPaladin copy() {
        return new AccorderPaladin(this);
    }

}
