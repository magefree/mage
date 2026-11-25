package mage.cards.w;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodlandChampion extends CardImpl {

    public WoodlandChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more tokens enter the battlefield under your control, put that many +1/+1 counters on Woodland Champion.
        this.addAbility(new WoodlandChampionTriggeredAbility());
    }

    private WoodlandChampion(final WoodlandChampion card) {
        super(card);
    }

    @Override
    public WoodlandChampion copy() {
        return new WoodlandChampion(this);
    }
}

class WoodlandChampionTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    WoodlandChampionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private WoodlandChampionTriggeredAbility(final WoodlandChampionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent instanceof PermanentToken && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int tokenCount = getFilteredEvents((ZoneChangeBatchEvent) event, game).size();
        if (tokenCount > 0) {
            this.getEffects().clear();
            this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(tokenCount)));
            return true;
        }
        return false;
    }

    @Override
    public WoodlandChampionTriggeredAbility copy() {
        return new WoodlandChampionTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more tokens you control enter, " +
                "put that many +1/+1 counters on {this}.";
    }
}
