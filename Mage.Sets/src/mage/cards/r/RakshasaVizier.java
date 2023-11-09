
package mage.cards.r;

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
import mage.game.events.ZoneChangeGroupEvent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RakshasaVizier extends CardImpl {

    public RakshasaVizier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.
        this.addAbility(new RakshasaVizierTriggeredAbility());
    }

    private RakshasaVizier(final RakshasaVizier card) {
        super(card);
    }

    @Override
    public RakshasaVizier copy() {
        return new RakshasaVizier(this);
    }
}

class RakshasaVizierTriggeredAbility extends TriggeredAbilityImpl {

    RakshasaVizierTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private RakshasaVizierTriggeredAbility(final RakshasaVizierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.GRAVEYARD == zEvent.getFromZone()
                && Zone.EXILED == zEvent.getToZone()
                && zEvent.getCards() != null) {
            int cardCount = 0;
            for (Card card : zEvent.getCards()) {
                if (card != null && card.isOwnedBy(getControllerId())) {
                    cardCount++;
                }
            }
            if (cardCount == 0) {
                return false;
            }
            this.getEffects().clear();
            this.getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance(cardCount)));
            return true;
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
