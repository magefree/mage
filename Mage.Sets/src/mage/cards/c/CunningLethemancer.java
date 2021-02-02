
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public final class CunningLethemancer extends CardImpl {

    public CunningLethemancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DiscardEachPlayerEffect()));
    }

    private CunningLethemancer(final CunningLethemancer card) {
        super(card);
    }

    @Override
    public CunningLethemancer copy() {
        return new CunningLethemancer(this);
    }
}
