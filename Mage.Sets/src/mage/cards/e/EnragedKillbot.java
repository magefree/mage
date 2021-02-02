
package mage.cards.e;

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
public final class EnragedKillbot extends CardImpl {

    public EnragedKillbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.KILLBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private EnragedKillbot(final EnragedKillbot card) {
        super(card);
    }

    @Override
    public EnragedKillbot copy() {
        return new EnragedKillbot(this);
    }
}
