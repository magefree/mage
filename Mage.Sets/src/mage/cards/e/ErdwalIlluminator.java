
package mage.cards.e;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ErdwalIlluminator extends CardImpl {

    public ErdwalIlluminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you investigate for the first time each turn, investigate an additional time.
        this.addAbility(new ErdwalIlluminatorTriggeredAbility());

    }

    public ErdwalIlluminator(final ErdwalIlluminator card) {
        super(card);
    }

    @Override
    public ErdwalIlluminator copy() {
        return new ErdwalIlluminator(this);
    }
}

class ErdwalIlluminatorTriggeredAbility extends TriggeredAbilityImpl {

    public ErdwalIlluminatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvestigateEffect(), false);
        addWatcher(new InvestigatedWatcher());
    }

    public ErdwalIlluminatorTriggeredAbility(final ErdwalIlluminatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.INVESTIGATED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        InvestigatedWatcher watcher = (InvestigatedWatcher) game.getState().getWatchers().get(InvestigatedWatcher.class.getSimpleName());
        return watcher != null && watcher.getTimesInvestigated(getControllerId()) == 1;
    }

    @Override
    public ErdwalIlluminatorTriggeredAbility copy() {
        return new ErdwalIlluminatorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you investigate for the first time each turn, investigate an additional time.";
    }
}

class InvestigatedWatcher extends Watcher {

    private final Map<UUID, Integer> timesInvestigated = new HashMap<>();

    public InvestigatedWatcher() {
        super(InvestigatedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public InvestigatedWatcher(final InvestigatedWatcher watcher) {
        super(watcher);
    }

    @Override
    public InvestigatedWatcher copy() {
        return new InvestigatedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.INVESTIGATED) {
            timesInvestigated.put(event.getPlayerId(), getTimesInvestigated(event.getPlayerId()) + 1);

        }
    }

    @Override
    public void reset() {
        super.reset();
        timesInvestigated.clear();
    }

    public int getTimesInvestigated(UUID playerId) {
        return timesInvestigated.getOrDefault(playerId, 0);
    }
}
