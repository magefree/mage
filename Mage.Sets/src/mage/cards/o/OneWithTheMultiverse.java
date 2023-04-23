package mage.cards.o;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OneWithTheMultiverse extends CardImpl {

    public OneWithTheMultiverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}{U}");

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands and cast spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect()));

        // Once during each of your turns, you may cast a spell from your hand or the top of your library without paying its mana cost.
        this.addAbility(new SimpleStaticAbility(new OneWithTheMultiverseEffect())
                .setIdentifier(MageIdentifier.OneWithTheMultiverseWatcher), new OneWithTheMultiverseWatcher());
    }

    private OneWithTheMultiverse(final OneWithTheMultiverse card) {
        super(card);
    }

    @Override
    public OneWithTheMultiverse copy() {
        return new OneWithTheMultiverse(this);
    }
}

class OneWithTheMultiverseEffect extends AsThoughEffectImpl {

    public OneWithTheMultiverseEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree, true);
        staticText = "once during each of your turns, you may cast a spell from your hand " +
                "or the top of your library without paying its mana cost.";
    }

    private OneWithTheMultiverseEffect(final OneWithTheMultiverseEffect effect) {
        super(effect);
    }

    @Override
    public OneWithTheMultiverseEffect copy() {
        return new OneWithTheMultiverseEffect(this);
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
        Card card = game.getCard(CardUtil.getMainCardId(game, objectId));
        OneWithTheMultiverseWatcher watcher = game.getState().getWatcher(OneWithTheMultiverseWatcher.class);
        if (controller == null
                || card == null
                || watcher == null
                || watcher.isAbilityUsed(new MageObjectReference(source))
                || !card.isOwnedBy(source.getControllerId())) {
            return false;
        }
        Zone zone = game.getState().getZone(card.getId());
        if (!Zone.HAND.match(zone) &&
                (!Zone.LIBRARY.match(zone) || !controller.getLibrary().getFromTop(game).getId().equals(card.getId()))) {
            return false;
        }
        allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
        return true;
    }
}

class OneWithTheMultiverseWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public OneWithTheMultiverseWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.OneWithTheMultiverseWatcher)) {
            usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
