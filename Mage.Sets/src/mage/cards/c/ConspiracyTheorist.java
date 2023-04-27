package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.CompositeCost;
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
import mage.game.events.DiscardedCardsEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

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
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1),
            new CompositeCost(new ManaCostsImpl<>("{1}"), new DiscardCardCost(), "pay {1} and discard a card"))
                .setText("you may pay {1} and discard a card. If you do, draw a card"), false));

        // Whenever you discard one or more nonland cards, you may exile one of them from your graveyard. If you do, you may cast it this turn.
        this.addAbility(new ConspiracyTheoristAbility());
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
            DiscardedCardsEvent discardedCardsEvent = (DiscardedCardsEvent) event;
            Set<Card> discardedNonLandCards = discardedCardsEvent.getDiscardedCards().getCards(StaticFilters.FILTER_CARD_NON_LAND, game);
            if (discardedNonLandCards.size() > 0) {
                this.getEffects().clear();
                this.getEffects().add(new ConspiracyTheoristEffect(discardedNonLandCards));
                return true;
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
            TargetCard target = new TargetCard(Zone.GRAVEYARD, new FilterCard("card to exile"));
            boolean validTarget = cards.stream()
                .anyMatch(card -> target.canTarget(card, game));
            if (validTarget && controller.chooseUse(Outcome.Benefit, "Exile a card?", source, game)) {
                if (controller.choose(Outcome.Benefit, cards, target, source, game)) {
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