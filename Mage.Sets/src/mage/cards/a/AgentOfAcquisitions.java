
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;

/**
 *
 * @author tiera3 - based on PrizefighterConstruct and CanalDredger
 * note - draftmatters ability not implemented
 */
public final class AgentOfAcquisitions extends CardImpl {

    public AgentOfAcquisitions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // TODO: Draft specific abilities not implemented
        // Draft Agent of Acquisitions face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft Agent of Acquisitions face up - not implemented.")));

        // Instead of drafting a card from a booster pack, you may draft each card in that booster pack, one at a time. If you do, turn Agent of Acquisitions face down and you can’t draft cards for the rest of this draft round.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Instead of drafting a card from a booster pack, "
                + "you may draft each card in that booster pack, one at a time. If you do, turn Agent of Acquisitions face down and "
                + "you can't draft cards for the rest of this draft round - not implemented.")));
    }

    private AgentOfAcquisitions(final AgentOfAcquisitions card) {
        super(card);
    }

    @Override
    public AgentOfAcquisitions copy() {
        return new AgentOfAcquisitions(this);
    }
}
