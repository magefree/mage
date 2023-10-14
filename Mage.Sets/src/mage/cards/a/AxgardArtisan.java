package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.BoostCountersAddedFirstTimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxgardArtisan extends CardImpl {

    public AxgardArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever one or more +1/+1 counters are put on Axgard Artisan for the first time each turn, create a Treasure token.
        this.addAbility(new AxgardArtisanTriggeredAbility());
    }

    private AxgardArtisan(final AxgardArtisan card) {
        super(card);
    }

    @Override
    public AxgardArtisan copy() {
        return new AxgardArtisan(this);
    }
}

class AxgardArtisanTriggeredAbility extends TriggeredAbilityImpl {

    AxgardArtisanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        this.setTriggerPhrase("Whenever one or more +1/+1 counters are put on {this} for the first time each turn, ");
        this.addWatcher(new BoostCountersAddedFirstTimeWatcher());
    }

    private AxgardArtisanTriggeredAbility(final AxgardArtisanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AxgardArtisanTriggeredAbility copy() {
        return new AxgardArtisanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && this.getSourceId().equals(event.getTargetId())
                && event.getData().equals(CounterType.P1P1.getName())
                && BoostCountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, 0);
    }
}
