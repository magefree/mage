package mage.abilities.common;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.replacement.SpellMorEnteringTappedEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Once during each of your turns, you may cast... from your graveyard
 * <p>
 * See Lurrus of the Dream Den and Rivaz of the Claw
 *
 * @author weirddan455, Susucr
 */
public class CastFromGraveyardOnceDuringEachOfYourTurnAbility extends SimpleStaticAbility {

    public CastFromGraveyardOnceDuringEachOfYourTurnAbility(FilterCard filter) {
        this(filter, (Cost) null);
    }

    public CastFromGraveyardOnceDuringEachOfYourTurnAbility(FilterCard filter, Cost additionalCost) {
        this(filter, additionalCost, MageIdentifier.OnceOnYourTurnCastFromGraveyard);
    }

    public CastFromGraveyardOnceDuringEachOfYourTurnAbility(FilterCard filter, MageIdentifier mageIdentifier) {
        this(filter, null, mageIdentifier);
    }

    public CastFromGraveyardOnceDuringEachOfYourTurnAbility(FilterCard filter, Cost additionalCost, MageIdentifier mageIdentifier) {
        super(new CastFromGraveyardOnceEffect(filter, additionalCost, mageIdentifier));
        this.addWatcher(new CastFromGraveyardOnceWatcher());
        switch (mageIdentifier) {
            case OnceOnYourTurnCastFromGraveyard:
            case OnceOnYourTurnCastFromGraveyardEntersTapped:
                this.setIdentifier(mageIdentifier);
                break;
            default:
                throw new IllegalArgumentException("Wrong code usage: only specific MageIdentifier are currently supported");
        }
    }

    private CastFromGraveyardOnceDuringEachOfYourTurnAbility(final CastFromGraveyardOnceDuringEachOfYourTurnAbility ability) {
        super(ability);
    }

    @Override
    public CastFromGraveyardOnceDuringEachOfYourTurnAbility copy() {
        return new CastFromGraveyardOnceDuringEachOfYourTurnAbility(this);
    }
}

class CastFromGraveyardOnceEffect extends AsThoughEffectImpl {

    private final FilterCard filter;
    private final Cost additionalCost;
    private final MageIdentifier mageIdentifier;

    CastFromGraveyardOnceEffect(FilterCard filter, Cost additionalCost, MageIdentifier mageIdentifier) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.staticText = "Once during each of your turns, you may cast " + filter.getMessage()
                + (filter.getMessage().contains("your graveyard") ? "" : " from your graveyard")
                + (additionalCost == null ? "" : " by " + additionalCost.getText() + " in addition to paying its other costs.");
        this.additionalCost = additionalCost;
        this.mageIdentifier = mageIdentifier;
    }

    private CastFromGraveyardOnceEffect(final CastFromGraveyardOnceEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.additionalCost = effect.additionalCost;
        this.mageIdentifier = effect.mageIdentifier;
    }

    @Override
    public CastFromGraveyardOnceEffect copy() {
        return new CastFromGraveyardOnceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        CastFromGraveyardOnceWatcher watcher = game.getState().getWatcher(CastFromGraveyardOnceWatcher.class);
        Card cardToCast = game.getCard(objectId);
        if (controller == null || sourcePermanent == null || watcher == null || cardToCast == null
                || !game.isActivePlayer(playerId) // only during your turn
                || !source.isControlledBy(playerId) // only you may cast
                || !Zone.GRAVEYARD.equals(game.getState().getZone(objectId)) // from graveyard
                || !cardToCast.getOwnerId().equals(playerId) // only your graveyard
                || !(affectedAbility instanceof SpellAbility) // characteristics to check
                || watcher.abilityUsed(new MageObjectReference(sourcePermanent, game)) // once per turn
        ) {
            return false;
        }
        SpellAbility spellAbility = (SpellAbility) affectedAbility;
        Card cardToCheck = spellAbility.getCharacteristics(game);
        if (spellAbility.getManaCosts().isEmpty()) {
            return false;
        }
        Set<MageIdentifier> allowedToBeCastNow = spellAbility.spellCanBeActivatedNow(playerId, game);
        if (!allowedToBeCastNow.contains(MageIdentifier.Default)
                || !filter.match(cardToCheck, playerId, source, game)) {
            return false;
        }
        if (additionalCost != null) {
            Costs<Cost> costs = new CostsImpl<>();
            costs.add(additionalCost);
            controller.setCastSourceIdWithAlternateMana(
                    objectId, spellAbility.getManaCosts(),
                    costs, mageIdentifier);
        }
        return true;
    }
}

class CastFromGraveyardOnceWatcher extends Watcher {

    // TODO: we might want to store (approver, approving ability) instead on the odd chance there
    //       is more than one such ability on a given approver. (event.getApprovingObject() has
    //       the exact ability, but not sure its id is stable enough.)
    // Set of each approver that approved casting a spell this turn (and is thus done for the turn)
    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    CastFromGraveyardOnceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!GameEvent.EventType.SPELL_CAST.equals(event.getType())) {
            return;
        }
        if (event.hasApprovingIdentifier(MageIdentifier.OnceOnYourTurnCastFromGraveyard)) {
            usedFrom.add(event.getApprovingObject().getApprovingMageObjectReference());
            return;
        }
        if (event.hasApprovingIdentifier(MageIdentifier.OnceOnYourTurnCastFromGraveyardEntersTapped)) {
            usedFrom.add(event.getApprovingObject().getApprovingMageObjectReference());
            // The cast (most likely permanent) spell enters the battlefield tapped.
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                MageObjectReference mor = new MageObjectReference(target, game);
                game.getState().addEffect(
                        new SpellMorEnteringTappedEffect(mor),
                        event.getApprovingObject().getApprovingAbility() // ability that approved the cast is the source of the tapping.
                );
            }
            return;
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean abilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
