package mage.abilities.common;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Once during each of your turns, you may cast... from your graveyard
 *
 * See Lurrus of the Dream Den and Rivaz of the Claw
 *
 * @author weirddan455
 */
public class CastFromGraveyardOnceStaticAbility extends SimpleStaticAbility {

    public CastFromGraveyardOnceStaticAbility(FilterCard filter, String text) {
        super(new CastFromGraveyardOnceEffect(filter, text));
        this.addWatcher(new CastFromGraveyardOnceWatcher());
        this.setIdentifier(MageIdentifier.CastFromGraveyardOnceWatcher);
    }

    private CastFromGraveyardOnceStaticAbility(final CastFromGraveyardOnceStaticAbility ability) {
        super(ability);
    }

    @Override
    public CastFromGraveyardOnceStaticAbility copy() {
        return new CastFromGraveyardOnceStaticAbility(this);
    }
}

class CastFromGraveyardOnceEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    CastFromGraveyardOnceEffect(FilterCard filter, String text) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit, true);
        this.filter = filter;
        this.staticText = text;
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
        if (source.isControlledBy(affectedControllerId)
                && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))
                && game.isActivePlayer(affectedControllerId)) {
            Card card = game.getCard(objectId);
            Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
            if (card != null && sourceObject != null
                    && card.isOwnedBy(affectedControllerId)
                    && card.getSpellAbility() != null
                    && card.getSpellAbility().spellCanBeActivatedRegularlyNow(affectedControllerId, game)
                    && filter.match(card, affectedControllerId, source, game)) {
                CastFromGraveyardOnceWatcher watcher = game.getState().getWatcher(CastFromGraveyardOnceWatcher.class);
                return watcher != null && watcher.abilityNotUsed(new MageObjectReference(sourceObject, game));
            }
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
