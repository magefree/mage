package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieRogueToken;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.SpellsCastWatcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class GeralfTheFleshwright extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombie");
    private static final Hint hint = new ValueHint("Number of Zombies that entered this turn", GeralfTheFleshwrightValue.instance);

    public GeralfTheFleshwright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell during your turn other than your first spell that turn, create a 2/2 blue and black Zombie Rogue creature token.
        this.addAbility(new GeralfTheFleshwrightTriggeredAbility());

        // Whenever a Zombie enters the battlefield under your control, put a +1/+1 counter on it for each other Zombie that entered the battlefield under your control this turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new GeralfTheFleshwrightEffect(),
                filter,
                false,
                SetTargetPointer.PERMANENT
        ).addHint(hint), new GeralfTheFleshwrightWatcher());
    }

    private GeralfTheFleshwright(final GeralfTheFleshwright card) {
        super(card);
    }

    @Override
    public GeralfTheFleshwright copy() {
        return new GeralfTheFleshwright(this);
    }
}


class GeralfTheFleshwrightTriggeredAbility extends TriggeredAbilityImpl {

    public GeralfTheFleshwrightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieRogueToken()));
        setTriggerPhrase("Whenever you cast a spell during your turn other than your first spell that turn, ");
    }

    private GeralfTheFleshwrightTriggeredAbility(final GeralfTheFleshwrightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GeralfTheFleshwrightTriggeredAbility copy() {
        return new GeralfTheFleshwrightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId()) && game.isActivePlayer(getControllerId())) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            return watcher != null && watcher.getSpellsCastThisTurn(event.getPlayerId()).size() > 1;
        }
        return false;
    }
}


class GeralfTheFleshwrightWatcher extends Watcher {

    // player -> MOR of zombies that entered this turn under that player control.
    private final Map<UUID, Set<MageObjectReference>> enteredThisTurn = new HashMap<>();

    GeralfTheFleshwrightWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.getSubtype().contains(SubType.ZOMBIE)) {
            return;
        }

        Player controller = game.getPlayer(event.getPlayerId());
        if (controller == null) {
            return;
        }
        enteredThisTurn.computeIfAbsent(controller.getId(), (k -> new HashSet<>()))
                .add(new MageObjectReference(permanent, game));
    }

    @Override
    public void reset() {
        super.reset();
        enteredThisTurn.clear();
    }

    int getZombiesThatEnteredThisTurn(UUID playerId, MageObjectReference toExclude) {
        if (toExclude == null) {
            return enteredThisTurn
                    .getOrDefault(playerId, Collections.emptySet())
                    .size();
        } else {
            return enteredThisTurn
                    .getOrDefault(playerId, Collections.emptySet())
                    .stream()
                    .filter(mor -> !toExclude.equals(mor))
                    .mapToInt(x -> 1)
                    .sum();
        }
    }
}

enum GeralfTheFleshwrightValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        GeralfTheFleshwrightWatcher watcher = game.getState().getWatcher(GeralfTheFleshwrightWatcher.class);
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (watcher == null || controller == null) {
            return 0;
        }
        return watcher.getZombiesThatEnteredThisTurn(controller.getId(), null);
    }

    @Override
    public GeralfTheFleshwrightValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "Number of Zombies that entered this turn";
    }
}

class GeralfTheFleshwrightEffect extends OneShotEffect {

    GeralfTheFleshwrightEffect() {
        super(Outcome.BoostCreature);
        staticText = "put a +1/+1 counter on it for each other Zombie that entered the battlefield under your control this turn";
    }

    private GeralfTheFleshwrightEffect(final GeralfTheFleshwrightEffect effect) {
        super(effect);
    }

    @Override
    public GeralfTheFleshwrightEffect copy() {
        return new GeralfTheFleshwrightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GeralfTheFleshwrightWatcher watcher = game.getState().getWatcher(GeralfTheFleshwrightWatcher.class);
        if (watcher == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int count = watcher.getZombiesThatEnteredThisTurn(source.getControllerId(), new MageObjectReference(permanent, game));
        if (count > 0) {
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(count))
                    .setTargetPointer(getTargetPointer().copy())
                    .apply(game, source);
        }
        return true;
    }
}
