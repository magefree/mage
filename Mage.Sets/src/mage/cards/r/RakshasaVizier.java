
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public final class RakshasaVizier extends CardImpl {

    public RakshasaVizier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}{U}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DEMON);


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.
        // TODO: Handle effects that move more than one card with one trigger (e.g. if opponent want to counter a trigger, he has now to counter multiple instead of one).
        this.addAbility(new RakshasaVizierTriggeredAbility());
    }

    public RakshasaVizier(final RakshasaVizier card) {
        super(card);
    }

    @Override
    public RakshasaVizier copy() {
        return new RakshasaVizier(this);
    }
}

class RakshasaVizierTriggeredAbility extends TriggeredAbilityImpl {

    public RakshasaVizierTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    public RakshasaVizierTriggeredAbility(final RakshasaVizierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.GRAVEYARD
                && zEvent.getToZone() == Zone.EXILED) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isOwnedBy(getControllerId())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on {this}.";
    }

    @Override
    public RakshasaVizierTriggeredAbility copy() {
        return new RakshasaVizierTriggeredAbility(this);
    }
}
