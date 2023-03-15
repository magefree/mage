
package mage.cards.u;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.common.PermanentWasCastWatcher;

/**
 *
 * @author chrvanorle
 */
public final class UphillBattle extends CardImpl {

    public UphillBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures played by your opponents enter the battlefield tapped.
        Ability tapAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new UphillBattleTapEffect());
        tapAbility.addWatcher(new PermanentWasCastWatcher());
        tapAbility.addWatcher(new PlayCreatureLandWatcher());
        addAbility(tapAbility);
    }

    private UphillBattle(final UphillBattle card) {
        super(card);
    }

    @Override
    public UphillBattle copy() {
        return new UphillBattle(this);
    }
}

class PlayCreatureLandWatcher extends Watcher {

    private final Set<UUID> playerPlayedLand = new HashSet<>(); // player that played land
    private final Set<UUID> landPlayed = new HashSet<>(); // land played

    public PlayCreatureLandWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND) {
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && card.isLand(game)
                    && card.isCreature(game)
                    && !playerPlayedLand.contains(event.getPlayerId())) {
                playerPlayedLand.add(event.getPlayerId());
                landPlayed.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        playerPlayedLand.clear();
        landPlayed.clear();
        super.reset();
    }

    public boolean landPlayed(UUID playerId) {
        return playerPlayedLand.contains(playerId);
    }

    public boolean wasLandPlayed(UUID landId) {
        return landPlayed.contains(landId);
    }
}

class UphillBattleTapEffect extends ReplacementEffectImpl {

    UphillBattleTapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Creatures played by your opponents enter the battlefield tapped";
    }

    UphillBattleTapEffect(final UphillBattleTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        PermanentWasCastWatcher creatureSpellWatcher = game.getState().getWatcher(PermanentWasCastWatcher.class);
        PlayCreatureLandWatcher landWatcher = game.getState().getWatcher(PlayCreatureLandWatcher.class);

        if (target != null
                && ((creatureSpellWatcher != null && creatureSpellWatcher.wasPermanentCastThisTurn(target.getId()))
                || (landWatcher != null && landWatcher.wasLandPlayed(target.getId())))) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
            if (permanent != null && permanent.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UphillBattleTapEffect copy() {
        return new UphillBattleTapEffect(this);
    }
}
