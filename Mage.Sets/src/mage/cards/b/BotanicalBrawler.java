package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.BoostCountersAddedFirstTimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BotanicalBrawler extends CardImpl {

    public BotanicalBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Botanical Brawler enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(2), true
        ), "with two +1/+1 counters on it"));

        // Whenever one or more +1/+1 counters are put on another permanent you control, if it's the first time +1/+1 counters have been put on that permanent this turn, put a +1/+1 counter on Botanical Brawler.
        this.addAbility(new BotanicalBrawlerTriggeredAbility());
    }

    private BotanicalBrawler(final BotanicalBrawler card) {
        super(card);
    }

    @Override
    public BotanicalBrawler copy() {
        return new BotanicalBrawler(this);
    }
}

class BotanicalBrawlerTriggeredAbility extends TriggeredAbilityImpl {

    BotanicalBrawlerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addWatcher(new BoostCountersAddedFirstTimeWatcher());
    }

    private BotanicalBrawlerTriggeredAbility(final BotanicalBrawlerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BotanicalBrawlerTriggeredAbility copy() {
        return new BotanicalBrawlerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        int offset = 0;
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
            offset++;
        }
        return permanent != null
                && !getSourceId().equals(event.getTargetId())
                && isControlledBy(permanent.getControllerId())
                && event.getData().equals(CounterType.P1P1.getName())
                && BoostCountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, offset);
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on another permanent you control, " +
                "if it's the first time +1/+1 counters have been put on that permanent this turn, " +
                "put a +1/+1 counter on {this}.";
    }
}
