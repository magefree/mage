package mage.cards.w;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author weirddan455
 */
public final class Wish extends CardImpl {

    public Wish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // You may play a card you own from outside the game this turn.
        this.getSpellAbility().addEffect(new WishEffect());
        this.getSpellAbility().setIdentifier(MageIdentifier.WishWatcher);
        this.getSpellAbility().addWatcher(new WishWatcher());
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private Wish(final Wish card) {
        super(card);
    }

    @Override
    public Wish copy() {
        return new Wish(this);
    }
}

class WishEffect extends OneShotEffect {

    public WishEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may play a card you own from outside the game this turn";
    }

    private WishEffect(final WishEffect effect) {
        super(effect);
    }

    @Override
    public WishEffect copy() {
        return new WishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getSideboard().isEmpty()) {
            controller.lookAtCards(source, "Sideboard", controller.getSideboard(), game);
            game.addEffect(new WishPlayFromSideboardEffect(), source);
            return true;
        }
        return false;
    }
}

class WishPlayFromSideboardEffect extends AsThoughEffectImpl {

    public WishPlayFromSideboardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private WishPlayFromSideboardEffect(final WishPlayFromSideboardEffect effect) {
        super(effect);
    }

    @Override
    public WishPlayFromSideboardEffect copy() {
        return new WishPlayFromSideboardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId)) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            UUID mainCardId = CardUtil.getMainCardId(game, objectId);
            if (controller != null && sourceObject != null && controller.getSideboard().contains(mainCardId)) {
                WishWatcher watcher = game.getState().getWatcher(WishWatcher.class);
                return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
            }
        }
        return false;
    }
}

class WishWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    WishWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((GameEvent.EventType.SPELL_CAST.equals(event.getType()) || GameEvent.EventType.LAND_PLAYED.equals(event.getType()))
                && event.hasApprovingIdentifier(MageIdentifier.WishWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
