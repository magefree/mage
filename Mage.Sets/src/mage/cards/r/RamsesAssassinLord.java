package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class RamsesAssassinLord extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ASSASSIN, "Assassins");

    public RamsesAssassinLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Other Assassins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        this.addAbility(new RamsesAssassinLordTriggeredAbility());
    }

    private RamsesAssassinLord(final RamsesAssassinLord card) {
        super(card);
    }

    @Override
    public RamsesAssassinLord copy() {
        return new RamsesAssassinLord(this);
    }
}

class RamsesAssassinLordTriggeredAbility extends TriggeredAbilityImpl {

    RamsesAssassinLordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WinGameSourceControllerEffect());
        this.addWatcher(new RamsesAssassinLordWatcher());
    }

    private RamsesAssassinLordTriggeredAbility(final RamsesAssassinLordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RamsesAssassinLordTriggeredAbility copy() {
        return new RamsesAssassinLordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return RamsesAssassinLordWatcher.check(event.getTargetId(), getSourceId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever a player loses the game, if they were attacked " +
                "this turn by an Assassin you controlled, you win the game.";
    }
}

class RamsesAssassinLordWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> map = new HashMap<>();

    RamsesAssassinLordWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED
                || game.getPlayer(event.getTargetId()) == null) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null && permanent.hasSubtype(SubType.ASSASSIN, game)) {
            map.computeIfAbsent(event.getTargetId(), x -> new HashSet<>()).add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean check(UUID defenderId, UUID controllerId, Game game) {
        return game
                .getState()
                .getWatcher(RamsesAssassinLordWatcher.class)
                .map
                .getOrDefault(defenderId, Collections.emptySet())
                .contains(controllerId);
    }
}
