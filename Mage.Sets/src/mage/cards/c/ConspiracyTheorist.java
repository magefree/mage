package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ConspiracyTheorist extends CardImpl {

    public ConspiracyTheorist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Conspiracy Theorist attacks, you may pay {1} and discard a card. If you do, draw a card.
        CostsImpl<Cost> costs = new CostsImpl<>();
        costs.add(new ManaCostsImpl<>("{1}"));
        costs.add(new DiscardCardCost());
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), costs).setText("you may pay {1} and discard a card. If you do, draw a card"), false));

        // Whenever you discard one or more nonland cards, you may exile one of them from your graveyard. If you do, you may cast it this turn.
        this.addAbility(new ConspiracyTheoristAbility(), new ConspiracyTheoristWatcher());
    }

    private ConspiracyTheorist(final ConspiracyTheorist card) {
        super(card);
    }

    @Override
    public ConspiracyTheorist copy() {
        return new ConspiracyTheorist(this);
    }
}

class ConspiracyTheoristAbility extends TriggeredAbilityImpl {

    ConspiracyTheoristAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    ConspiracyTheoristAbility(ConspiracyTheoristAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            ConspiracyTheoristWatcher watcher = game.getState().getWatcher(ConspiracyTheoristWatcher.class);
            if (watcher != null) {
                CardsImpl discardedCards = watcher.getSourceIdToDiscardedCards().getOrDefault(event.getSourceId(), new CardsImpl());
                Set<Card> discardedNonLandCards = discardedCards.getCards(StaticFilters.FILTER_CARD_NON_LAND, game);
                if (discardedNonLandCards.size() > 0) {
                    this.getEffects().clear();
                    this.getEffects().add(new ConspiracyTheoristEffect(discardedNonLandCards));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ConspiracyTheoristAbility copy() {
        return new ConspiracyTheoristAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you discard one or more nonland cards, you may exile one of them from your graveyard. If you do, you may cast it this turn.";
    }
}

class ConspiracyTheoristEffect extends OneShotEffect {

    private final Set<Card> discardedCards;

    ConspiracyTheoristEffect(Set<Card> discardedCards) {
        super(Outcome.Benefit);
        this.discardedCards = discardedCards;
    }

    ConspiracyTheoristEffect(ConspiracyTheoristEffect effect) {
        super(effect);
        this.discardedCards = effect.discardedCards;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl cards = new CardsImpl(discardedCards);
            if (controller.chooseUse(Outcome.Exile, "Exile a card?", source, game)) {
                TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard("card to exile"));
                if (controller.choose(Outcome.Exile, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null && controller.moveCards(card, Zone.EXILED, source, game)) {
                        // you may cast it this turn
                        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ConspiracyTheoristEffect copy() {
        return new ConspiracyTheoristEffect(this);
    }
}

class ConspiracyTheoristWatcher extends Watcher {

    private final Map<UUID, CardsImpl> sourceIdToDiscardedCards = new HashMap<>();

    ConspiracyTheoristWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            Card card = game.getCard(event.getTargetId());
            sourceIdToDiscardedCards.merge(event.getSourceId(), new CardsImpl(card), (c1, c2) -> {
               c1.addAll(c2.getCards(game));
               return c1;
            });
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.sourceIdToDiscardedCards.clear();
    }

    public Map<UUID, CardsImpl> getSourceIdToDiscardedCards() {
        return sourceIdToDiscardedCards;
    }
}