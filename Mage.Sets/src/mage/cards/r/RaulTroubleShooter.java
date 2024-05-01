package mage.cards.r;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class RaulTroubleShooter extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell from among cards in your graveyard that were milled this turn");

    static {
        filter.add(RaulTroubleShooterPredicate.instance);
    }

    public RaulTroubleShooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Once during each of your turns, you may cast a spell from among cards in your graveyard that were milled this turn.
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter), new RaulTroubleShooterWatcher());

        // {T}: Each player mills a card.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsEachPlayerEffect(1, TargetController.ANY),
                new TapSourceCost()
        ));
    }

    private RaulTroubleShooter(final RaulTroubleShooter card) {
        super(card);
    }

    @Override
    public RaulTroubleShooter copy() {
        return new RaulTroubleShooter(this);
    }
}

enum RaulTroubleShooterPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return RaulTroubleShooterWatcher.wasMilledThisTurn(
                input.getPlayerId(),
                new MageObjectReference(input.getObject().getMainCard(), game),
                game
        );
    }
}

class RaulTroubleShooterWatcher extends Watcher {

    // player -> set of Cards mor (the main card's mor) milled this turn.
    private final Map<UUID, Set<MageObjectReference>> milledThisTurn = new HashMap<>();

    RaulTroubleShooterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MILLED_CARD) {
            return;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return;
        }
        Card mainCard = card.getMainCard();
        if (game.getState().getZone(mainCard.getId()) != Zone.GRAVEYARD) {
            // Ensure that the current zone is indeed the graveyard
            return;
        }
        milledThisTurn.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>());
        milledThisTurn.get(event.getPlayerId()).add(new MageObjectReference(mainCard, game));
    }

    @Override
    public void reset() {
        super.reset();
        milledThisTurn.clear();
    }

    static boolean wasMilledThisTurn(UUID playerId, MageObjectReference morMainCard, Game game) {
        RaulTroubleShooterWatcher watcher = game.getState().getWatcher(RaulTroubleShooterWatcher.class);
        return watcher != null && watcher
                .milledThisTurn
                .getOrDefault(playerId, Collections.emptySet())
                .contains(morMainCard);
    }
}
