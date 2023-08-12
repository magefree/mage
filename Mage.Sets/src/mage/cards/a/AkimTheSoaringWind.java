package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.BirdToken;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author AsterAether
 */
public final class AkimTheSoaringWind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public AkimTheSoaringWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you create one or more tokens for the first time each turn, create a 1/1 white Bird creature token with flying.
        this.addAbility(
                new AkimTheSoaringTokenAbility()
                        .addHint(new ConditionHint(AkimTheSoaringWindCondition.instance,
                                "You create one or more tokens for the first time each turn")),
                new AkimTheSoaringWindWatcher()
        );

        // {3}{U}{R}{W}: Creature tokens you control gain double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilityControlledEffect(
                        DoubleStrikeAbility.getInstance(),
                        Duration.EndOfTurn,
                        filter,
                        false),
                new ManaCostsImpl<>("{3}{U}{R}{W}"))
        );
    }

    private AkimTheSoaringWind(final AkimTheSoaringWind card) {
        super(card);
    }

    @Override
    public AkimTheSoaringWind copy() {
        return new AkimTheSoaringWind(this);
    }
}

enum AkimTheSoaringWindCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        AkimTheSoaringWindWatcher watcher = game.getState().getWatcher(AkimTheSoaringWindWatcher.class);
        return watcher != null && watcher.firstToken(controller.getId());
    }

    @Override
    public String toString() {
        return "you create one or more tokens for the first time each turn";
    }
}

class AkimTheSoaringTokenAbility extends TriggeredAbilityImpl {

    public AkimTheSoaringTokenAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BirdToken(), 1), false);
    }

    public AkimTheSoaringTokenAbility(final AkimTheSoaringTokenAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKEN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!AkimTheSoaringWindCondition.instance.apply(game, this)) {
            return false;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isControlledBy(this.getControllerId());
    }

    @Override
    public TriggeredAbility copy() {
        return new AkimTheSoaringTokenAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you create one or more tokens for the first time each turn, "
                + "create a 1/1 white Bird creature token with flying.";
    }
}

class AkimTheSoaringWindWatcher extends Watcher {

    public enum TokenState {
        NoToken,
        FirstToken,
        MoreThanOneToken
    }

    private final Map<UUID, TokenState> playerIds = new HashMap<>();

    AkimTheSoaringWindWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CREATED_TOKEN) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent instanceof PermanentToken) {
            if (!playerIds.containsKey(permanent.getControllerId())) {
                playerIds.put(permanent.getControllerId(), TokenState.FirstToken);
            } else {
                playerIds.put(permanent.getControllerId(), TokenState.MoreThanOneToken);
            }
        }
    }

    @Override
    public void reset() {
        playerIds.clear();
    }

    boolean firstToken(UUID playerId) {
        return playerIds.getOrDefault(playerId, TokenState.NoToken) == TokenState.FirstToken;
    }
}