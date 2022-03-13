package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class MemoryJar extends CardImpl {

    public MemoryJar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {T}, Sacrifice Memory Jar: Each player exiles all cards from their hand face down and draws seven cards.
        // At the beginning of the next end step, each player discards their hand and returns to their hand each
        //card they exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MemoryJarEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MemoryJar(final MemoryJar card) {
        super(card);
    }

    @Override
    public MemoryJar copy() {
        return new MemoryJar(this);
    }
}

class MemoryJarEffect extends OneShotEffect {

    MemoryJarEffect() {
        super(Outcome.DrawCard);
        staticText = "Each player exiles all cards from their hand face down and draws seven cards. " +
                "At the beginning of the next end step, each player discards their hand " +
                "and returns to their hand each card they exiled this way.";
    }

    private MemoryJarEffect(final MemoryJarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        //Exile hand
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            cards.addAll(player.getHand());
            player.moveCards(player.getHand(), Zone.EXILED, source, game);
        }
        //Draw 7 cards
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(7, source, game);
            }
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        cards.getCards(game).stream().filter(Objects::nonNull).forEach(card -> card.setFaceDown(true, game));
        //Delayed ability
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new MemoryJarDelayedEffect(cards)), source);
        return true;
    }

    @Override
    public MemoryJarEffect copy() {
        return new MemoryJarEffect(this);
    }
}

class MemoryJarDelayedEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    private final Cards cards = new CardsImpl();

    MemoryJarDelayedEffect(Cards cards) {
        super(Outcome.DrawCard);
        this.cards.addAll(cards);
        staticText = "each player discards their hand and returns to their hand each card they exiled this way";
    }

    private MemoryJarDelayedEffect(final MemoryJarDelayedEffect effect) {
        super(effect);
        this.cards.addAll(effect.cards);
    }

    @Override
    public MemoryJarDelayedEffect copy() {
        return new MemoryJarDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.discard(player.getHand(), false, source, game);
            player.moveCards(cards.getCards(filter, playerId, source, game), Zone.HAND, source, game);
        }
        return true;
    }
}
