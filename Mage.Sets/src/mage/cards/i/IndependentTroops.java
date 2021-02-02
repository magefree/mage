
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class IndependentTroops extends CardImpl {

    public IndependentTroops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private IndependentTroops(final IndependentTroops card) {
        super(card);
    }

    @Override
    public IndependentTroops copy() {
        return new IndependentTroops(this);
    }
}
