
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class TolarianSerpent extends CardImpl {

    public TolarianSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, put the top seven cards of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, 
                "beginning of your upkeep", 
                new MillCardsControllerEffect(7), false));
    }

    public TolarianSerpent(final TolarianSerpent card) {
        super(card);
    }

    @Override
    public TolarianSerpent copy() {
        return new TolarianSerpent(this);
    }
}
