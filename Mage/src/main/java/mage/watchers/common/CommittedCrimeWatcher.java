package mage.watchers.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CommittedCrimeWatcher extends Watcher {

    private final Set<UUID> criminals = new HashSet<>();

    public CommittedCrimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.TARGETED) {
            return;
        }
        UUID controllerId = game.getControllerId(event.getSourceId());
        if (controllerId == null) {
            return;
        }
        Set<UUID> opponents = game.getOpponents(controllerId);
        if (opponents.contains(event.getTargetId())) {
            criminals.add(controllerId);
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (opponents.contains(permanent.getControllerId())) {
                criminals.add(controllerId);
            }
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null) {
            if (opponents.contains(spell.getControllerId())) {
                criminals.add(controllerId);
            }
            return;
        }
        Card card = game.getSpell(event.getTargetId());
        if (card != null && game.getState().getZone(event.getTargetId()) != Zone.EXILED) {
            if (opponents.contains(card.getOwnerId())) {
                criminals.add(controllerId);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        criminals.clear();
    }

    public static boolean checkCriminality(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CommittedCrimeWatcher.class)
                .criminals
                .contains(source.getSourceId());
    }
}
