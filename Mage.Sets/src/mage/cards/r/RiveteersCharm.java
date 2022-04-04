package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.MaxManaValueControlledPermanentPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker they control with the highest mana value " +
                    "among creatures and planeswalkers they control"
    );

    static {
        filter.add(MaxManaValueControlledPermanentPredicate.instance);
    }

    public RiveteersCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{R}{G}");

        // Choose one —
        // • Target opponent sacrifices a creature or planeswalker they control with the highest mana value among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "target opponent"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Exile the top three cards of your library. Until your next end step, you may play those cards.
        this.getSpellAbility().addMode(new Mode(new RiveteersCharmEffect()));
        this.getSpellAbility().addWatcher(new RiveteersCharmWatcher());

        // • Exile target player's graveyard.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllTargetPlayerEffect()).addTarget(new TargetPlayer()));
    }

    private RiveteersCharm(final RiveteersCharm card) {
        super(card);
    }

    @Override
    public RiveteersCharm copy() {
        return new RiveteersCharm(this);
    }
}

class RiveteersCharmEffect extends OneShotEffect {

    RiveteersCharmEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top three cards of your library. Until your next end step, you may play those cards";
    }

    private RiveteersCharmEffect(final RiveteersCharmEffect effect) {
        super(effect);
    }

    @Override
    public RiveteersCharmEffect copy() {
        return new RiveteersCharmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        int count = RiveteersCharmWatcher.getCount(game, source);
        Condition condition = (g, s) -> RiveteersCharmWatcher.getCount(g, s) == count;
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, false, null, condition);
        }
        return true;
    }
}

class RiveteersCharmWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    RiveteersCharmWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case END_TURN_STEP_PRE:
                playerMap.compute(game.getActivePlayerId(), CardUtil::setOrIncrementValue);
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    playerMap.clear();
                }
        }
    }

    static int getCount(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(RiveteersCharmWatcher.class)
                .playerMap
                .getOrDefault(source.getControllerId(), 0);
    }
}
