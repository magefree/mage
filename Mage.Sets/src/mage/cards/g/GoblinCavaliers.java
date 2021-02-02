
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class GoblinCavaliers extends CardImpl {

    public GoblinCavaliers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private GoblinCavaliers(final GoblinCavaliers card) {
        super(card);
    }

    @Override
    public GoblinCavaliers copy() {
        return new GoblinCavaliers(this);
    }
}
