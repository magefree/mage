package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author spjspj
 */
public final class Arboria extends CardImpl {

    public Arboria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        this.supertype.add(SuperType.WORLD);

        // Creatures can't attack a player unless that player cast a spell or put a nontoken permanent onto the battlefield during their last turn.
        Ability ability = new SimpleStaticAbility(new ArboriaEffect());
        ability.addWatcher(new PermanentsEnteredBattlefieldYourLastTurnWatcher());
        ability.addWatcher(new CastSpellYourLastTurnWatcher());
        this.addAbility(ability);
    }

    private Arboria(final Arboria card) {
        super(card);
    }

    @Override
    public Arboria copy() {
        return new Arboria(this);
    }
}

class ArboriaEffect extends RestrictionEffect {

    ArboriaEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures can't attack a player unless that player cast a spell or put a nontoken permanent onto the battlefield during their last turn";
    }

    private ArboriaEffect(final ArboriaEffect effect) {
        super(effect);
    }

    @Override
    public ArboriaEffect copy() {
        return new ArboriaEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (!game.getPlayers().containsKey(defenderId)) {
            return true;
        }

        CastSpellYourLastTurnWatcher watcher = game.getState().getWatcher(CastSpellYourLastTurnWatcher.class);
        if (watcher != null && watcher.getAmountOfSpellsCastOnPlayersTurn(defenderId) > 0) {
            return true;
        }

        PermanentsEnteredBattlefieldYourLastTurnWatcher watcher2
                = game.getState().getWatcher(PermanentsEnteredBattlefieldYourLastTurnWatcher.class);

        if (watcher2 != null && watcher2.getPermanentsEnteringOnPlayersLastTurn(game, defenderId) != null) {
            for (Permanent permanent : watcher2.getPermanentsEnteringOnPlayersLastTurn(game, defenderId)) {
                if (permanent != null && !(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }
}

class CastSpellYourLastTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfSpellsCastOnPrevTurn = new HashMap<>();
    private final Map<UUID, Integer> amountOfSpellsCastOnCurrentTurn = new HashMap<>();
    private UUID lastActivePlayer = null;

    CastSpellYourLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        lastActivePlayer = game.getActivePlayerId();
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = event.getPlayerId();
            if (playerId != null && playerId.equals(lastActivePlayer)) {
                amountOfSpellsCastOnCurrentTurn.putIfAbsent(playerId, 0);
                amountOfSpellsCastOnCurrentTurn.compute(playerId, (k, a) -> a + 1);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        if (lastActivePlayer != null && amountOfSpellsCastOnPrevTurn.get(lastActivePlayer) != null) {
            amountOfSpellsCastOnPrevTurn.remove(lastActivePlayer);
        }

        amountOfSpellsCastOnPrevTurn.putAll(amountOfSpellsCastOnCurrentTurn);
        amountOfSpellsCastOnCurrentTurn.clear();
        lastActivePlayer = null;
    }

    public Integer getAmountOfSpellsCastOnPlayersTurn(UUID playerId) {
        return amountOfSpellsCastOnPrevTurn.getOrDefault(playerId, 0);
    }

}

class PermanentsEnteredBattlefieldYourLastTurnWatcher extends Watcher {

    private final Map<UUID, List<Permanent>> enteringBattlefield = new HashMap<>();
    private final Map<UUID, List<Permanent>> enteringBattlefieldLastTurn = new HashMap<>();
    private UUID lastActivePlayer = null;

    PermanentsEnteredBattlefieldYourLastTurnWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        lastActivePlayer = game.getActivePlayerId();

        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanentEntering(event.getTargetId());
            if (perm == null) {
                perm = game.getPermanent(event.getTargetId());
            }
            if (perm != null) {
                List<Permanent> permanents;
                if (!enteringBattlefield.containsKey(perm.getControllerId())) {
                    permanents = new ArrayList<>();
                    enteringBattlefield.put(perm.getControllerId(), permanents);
                } else {
                    permanents = enteringBattlefield.get(perm.getControllerId());
                }
                permanents.add(perm.copy()); // copy needed because attributes like color could be changed later
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        if (lastActivePlayer != null && enteringBattlefieldLastTurn.get(lastActivePlayer) != null) {
            enteringBattlefieldLastTurn.remove(lastActivePlayer);
        }
        enteringBattlefieldLastTurn.putAll(enteringBattlefield);
        enteringBattlefield.clear();
        lastActivePlayer = null;
    }

    public List<Permanent> getPermanentsEnteringOnPlayersLastTurn(Game game, UUID playerId) {
        if (game.isActivePlayer(playerId)) {
            return enteringBattlefield.get(playerId);
        }
        return enteringBattlefieldLastTurn.get(playerId);
    }
}
