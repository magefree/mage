
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct
 * note - draftmatters ability not implemented
 */
public final class AgentOfAcquisitions extends CardImpl {

    public AgentOfAcquisitions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private AgentOfAcquisitions(final AgentOfAcquisitions card) {
        super(card);
    }

    @Override
    public AgentOfAcquisitions copy() {
        return new AgentOfAcquisitions(this);
    }
}
