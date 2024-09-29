

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GoblinRoughrider extends CardImpl {

    public GoblinRoughrider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.KNIGHT);
        this.color.setRed(true);        
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private GoblinRoughrider(final GoblinRoughrider card) {
        super(card);
    }

    @Override
    public GoblinRoughrider copy() {
        return new GoblinRoughrider(this);
    }

}
