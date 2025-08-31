package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarAang extends CardImpl {

    public AvatarAang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.a.AangMasterOfElements.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // Whenever you waterbend, earthbend, firebend, or airbend, draw a card. Then if you've done all four this turn, transform Avatar Aang.
        this.addAbility(new AvatarAangTriggeredAbility());
    }

    private AvatarAang(final AvatarAang card) {
        super(card);
    }

    @Override
    public AvatarAang copy() {
        return new AvatarAang(this);
    }
}

class AvatarAangTriggeredAbility extends TriggeredAbilityImpl {

    private enum AvatarAangCondition implements Condition {
        instance;

        @Override
        public boolean apply(Game game, Ability source) {
            return AvatarAangWatcher.checkPlayer(game, source);
        }
    }

    AvatarAangTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), AvatarAangCondition.instance,
                "Then if you've done all four this turn, transform {this}"
        ));
        this.setTriggerPhrase("Whenever you waterbend, earthbend, firebend, or airbend, ");
        this.addWatcher(new AvatarAangWatcher());
    }

    private AvatarAangTriggeredAbility(final AvatarAangTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvatarAangTriggeredAbility copy() {
        return new AvatarAangTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case EARTHBENDED:
            case AIRBENDED:
            case FIREBENDED:
            case WATERBENDED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}

class AvatarAangWatcher extends Watcher {

    private final Set<UUID> earthSet = new HashSet<>();
    private final Set<UUID> airSet = new HashSet<>();
    private final Set<UUID> fireSet = new HashSet<>();
    private final Set<UUID> waterSet = new HashSet<>();

    AvatarAangWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case EARTHBENDED:
                earthSet.add(event.getPlayerId());
                return;
            case AIRBENDED:
                airSet.add(event.getPlayerId());
                return;
            case FIREBENDED:
                fireSet.add(event.getPlayerId());
                return;
            case WATERBENDED:
                waterSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        earthSet.clear();
        airSet.clear();
        fireSet.clear();
        earthSet.clear();
    }

    private boolean checkPlayer(UUID playerId) {
        return earthSet.contains(playerId)
                && airSet.contains(playerId)
                && fireSet.contains(playerId)
                && earthSet.contains(playerId);
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game.getState().getWatcher(AvatarAangWatcher.class).checkPlayer(source.getControllerId());
    }
}
