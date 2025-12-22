package mage.cards.c;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrampedVentsAccessMaze extends RoomCard {

    public CrampedVentsAccessMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{B}", "{5}{B}{B}");

        // Cramped Vents
        // When you unlock this door, this Room deals 6 damage to target creature an opponent controls. You gain life equal to the excess damage dealt this way.
        Ability ability = new UnlockThisDoorTriggeredAbility(new CrampedVentsEffect(), false, true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addWatcher(new AccessMazeWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Access Maze
        // Once during each of your turns, you may cast a spell from your hand by paying life equal to its mana value rather than paying its mana cost.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new AccessMazeEffect())
                .setIdentifier(MageIdentifier.AccessMazeWatcher));
    }

    private CrampedVentsAccessMaze(final CrampedVentsAccessMaze card) {
        super(card);
    }

    @Override
    public CrampedVentsAccessMaze copy() {
        return new CrampedVentsAccessMaze(this);
    }
}

class CrampedVentsEffect extends OneShotEffect {

    CrampedVentsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 6 damage to target creature an opponent controls. " +
                "You gain life equal to the excess damage dealt this way.";
    }

    private CrampedVentsEffect(final CrampedVentsEffect effect) {
        super(effect);
    }

    @Override
    public CrampedVentsEffect copy() {
        return new CrampedVentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(6, source, game);
        if (excess > 0) {
            Optional.ofNullable(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.gainLife(excess, game, source));
        }
        return true;
    }
}

class AccessMazeEffect extends AsThoughEffectImpl {

    AccessMazeEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree);
        staticText = "once during each of your turns, you may cast a spell from your hand " +
                "by paying life equal to its mana value rather than paying its mana cost";
    }

    private AccessMazeEffect(final AccessMazeEffect effect) {
        super(effect);
    }

    @Override
    public AccessMazeEffect copy() {
        return new AccessMazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Card card = game.getCard(CardUtil.getMainCardId(game, objectId));
        if (controller == null || card == null || sourceObject == null
                || !Zone.HAND.match(game.getState().getZone(card.getId()))
                || AccessMazeWatcher.isAbilityUsed(new MageObjectReference(sourceObject, game), game)) {
            return false;
        }
        Costs costs = new CostsImpl();
        costs.add(new PayLifeCost(card.getManaValue()));
        controller.setCastSourceIdWithAlternateMana(
                source.getSourceId(), null, costs, MageIdentifier.AccessMazeWatcher
        );
        return true;
    }
}

class AccessMazeWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public AccessMazeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.AccessMazeWatcher)) {
            usedFrom.add(event.getApprovingObject().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    static boolean isAbilityUsed(MageObjectReference mor, Game game) {
        return game
                .getState()
                .getWatcher(AccessMazeWatcher.class)
                .usedFrom
                .contains(mor);
    }
}
