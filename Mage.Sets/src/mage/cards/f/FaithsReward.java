package mage.cards.f;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Loki
 */
public final class FaithsReward extends CardImpl {

    public FaithsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new FaithsRewardEffect());
        this.getSpellAbility().addWatcher(new FaithsRewardWatcher());
    }

    private FaithsReward(final FaithsReward card) {
        super(card);
    }

    @Override
    public FaithsReward copy() {
        return new FaithsReward(this);
    }
}

class FaithsRewardEffect extends OneShotEffect {

    FaithsRewardEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn";
    }

    private FaithsRewardEffect(final FaithsRewardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        FaithsRewardWatcher watcher = game.getState().getWatcher(FaithsRewardWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        return player.moveCards(watcher.getCards(source.getControllerId(), game), Zone.BATTLEFIELD, source, game);
    }

    @Override
    public FaithsRewardEffect copy() {
        return new FaithsRewardEffect(this);
    }
}

class FaithsRewardWatcher extends Watcher {

    private final Set<MageObjectReference> morMap = new HashSet<>();

    FaithsRewardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            morMap.add(new MageObjectReference(((ZoneChangeEvent) event).getTarget(), game));
        }
    }

    Cards getCards(UUID ownerId, Game game) {
        Cards cards = new CardsImpl();
        morMap.stream()
                .map(m -> m.getCard(game))
                .filter(Objects::nonNull)
                .filter(MageObject::isPermanent)
                .filter(c -> c.isOwnedBy(ownerId))
                .forEach(cards::add);
        return cards;
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
    }
}
