
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class DelightedKillbot extends CardImpl {

    public DelightedKillbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.KILLBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private DelightedKillbot(final DelightedKillbot card) {
        super(card);
    }

    @Override
    public DelightedKillbot copy() {
        return new DelightedKillbot(this);
    }
}
