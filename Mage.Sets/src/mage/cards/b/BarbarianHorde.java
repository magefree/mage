
package mage.cards.b;

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
public final class BarbarianHorde extends CardImpl {

    public BarbarianHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN, SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private BarbarianHorde(final BarbarianHorde card) {
        super(card);
    }

    @Override
    public BarbarianHorde copy() {
        return new BarbarianHorde(this);
    }
}
