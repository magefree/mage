package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
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
public final class KeenEaredSentry extends CardImpl {

    public KeenEaredSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // Your opponents can't venture into the dungeon more than once each turn.
        this.addAbility(new SimpleStaticAbility(new KeenEaredSentryEffect()), new KeenEaredSentryWatcher());
    }

    private KeenEaredSentry(final KeenEaredSentry card) {
        super(card);
    }

    @Override
    public KeenEaredSentry copy() {
        return new KeenEaredSentry(this);
    }
}

class KeenEaredSentryEffect extends ContinuousRuleModifyingEffectImpl {

    KeenEaredSentryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "each opponent can't venture into the dungeon more than once each turn";
    }

    private KeenEaredSentryEffect(final KeenEaredSentryEffect effect) {
        super(effect);
    }

    @Override
    public KeenEaredSentryEffect copy() {
        return new KeenEaredSentryEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VENTURE;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return null;
        }
        return "You can't venture into the dungeon more than once each turn. (" + sourceObject.getName() + ')';
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getTargetId())
                && KeenEaredSentryWatcher.checkPlayer(event.getTargetId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class KeenEaredSentryWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    KeenEaredSentryWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.VENTURED) {
            playerSet.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        KeenEaredSentryWatcher watcher = game.getState().getWatcher(KeenEaredSentryWatcher.class);
        return watcher != null && watcher.playerSet.contains(playerId);
    }
}
