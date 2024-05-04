package mage.abilities.common;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
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
 * @author weirddan455
 */
public class CastFromGraveyardOnceEachTurnAbility extends SimpleStaticAbility {

    public CastFromGraveyardOnceEachTurnAbility(FilterCard filter) {
        super(new CastFromGraveyardOnceEffect(filter));
        this.addWatcher(new CastFromGraveyardOnceWatcher());
        this.setIdentifier(MageIdentifier.CastFromGraveyardOnceWatcher);
    }

    private CastFromGraveyardOnceEachTurnAbility(final CastFromGraveyardOnceEachTurnAbility ability) {
        super(ability);
    }

    @Override
    public CastFromGraveyardOnceEachTurnAbility copy() {
        return new CastFromGraveyardOnceEachTurnAbility(this);
    }
}

class CastFromGraveyardOnceEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    CastFromGraveyardOnceEffect(FilterCard filter) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.staticText = "Once during each of your turns, you may cast " + filter.getMessage()
                + (filter.getMessage().contains("your graveyard") ? "" : " from your graveyard");
    }

    private CastFromGraveyardOnceEffect(final CastFromGraveyardOnceEffect effect) {
        super(effect);
        this.filter = effect.filter;
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
        if (controller == null || sourcePermanent == null || watcher == null) {
            return false;
        }
        if (game.isActivePlayer(playerId) // only during your turn
                && source.isControlledBy(playerId) // only you may cast
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId)) // from graveyard
                && affectedAbility instanceof SpellAbility // characteristics to check
                && watcher.abilityNotUsed(new MageObjectReference(sourcePermanent, game)) // once per turn
        ) {
            SpellAbility spellAbility = (SpellAbility) affectedAbility;
            Card cardToCheck = spellAbility.getCharacteristics(game);
            if (spellAbility.getManaCosts().isEmpty()) {
                return false;
            }
            return spellAbility.spellCanBeActivatedRegularlyNow(playerId, game)
                    && filter.match(cardToCheck, playerId, source, game);
        }
        return false;
    }
}

class CastFromGraveyardOnceWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    CastFromGraveyardOnceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.CastFromGraveyardOnceWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean abilityNotUsed(MageObjectReference mor) {
        return !usedFrom.contains(mor);
    }
}
