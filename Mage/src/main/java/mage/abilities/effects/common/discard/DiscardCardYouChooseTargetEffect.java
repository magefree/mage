package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author noxx
 */
public class DiscardCardYouChooseTargetEffect extends OneShotEffect {

    private FilterCard filter;
    private TargetController targetController;
    private DynamicValue numberCardsToReveal;
    private final DynamicValue numberCardsToDiscard;
    private boolean revealAllCards;

    public DiscardCardYouChooseTargetEffect() {
        this(StaticFilters.FILTER_CARD_A);
    }

    public DiscardCardYouChooseTargetEffect(TargetController targetController) {
        this(StaticFilters.FILTER_CARD_A, targetController);
    }

    public DiscardCardYouChooseTargetEffect(FilterCard filter) {
        this(filter, TargetController.OPPONENT);
    }

    public DiscardCardYouChooseTargetEffect(FilterCard filter, TargetController targetController) {
        this(StaticValue.get(1), filter, targetController);
    }

    public DiscardCardYouChooseTargetEffect(int numberCardsToDiscard, TargetController targetController) {
        this(StaticValue.get(numberCardsToDiscard), targetController);
    }

    public DiscardCardYouChooseTargetEffect(DynamicValue numberCardsToDiscard, TargetController targetController) {
        this(numberCardsToDiscard, StaticFilters.FILTER_CARD_CARDS, targetController);
    }

    public DiscardCardYouChooseTargetEffect(DynamicValue numberCardsToDiscard,
                                            FilterCard filter, TargetController targetController) {
        super(Outcome.Discard);
        this.targetController = targetController;
        this.filter = filter;

        this.numberCardsToDiscard = numberCardsToDiscard;
        this.numberCardsToReveal = null;
        this.revealAllCards = true;

        staticText = this.setText();
    }

    public DiscardCardYouChooseTargetEffect(TargetController targetController, int numberCardsToReveal) {
        this(targetController, StaticValue.get(numberCardsToReveal));
    }

    public DiscardCardYouChooseTargetEffect(TargetController targetController, DynamicValue numberCardsToReveal) {
        this(StaticValue.get(1), StaticFilters.FILTER_CARD_A, targetController, numberCardsToReveal);
    }

    public DiscardCardYouChooseTargetEffect(int numberCardsToDiscard, TargetController targetController, int numberCardsToReveal) {
        this(StaticValue.get(numberCardsToDiscard), StaticFilters.FILTER_CARD_CARDS, targetController, StaticValue.get(numberCardsToReveal));
    }

    public DiscardCardYouChooseTargetEffect(DynamicValue numberCardsToDiscard, FilterCard filter, TargetController targetController, DynamicValue numberCardsToReveal) {
        super(Outcome.Discard);
        this.targetController = targetController;
        this.filter = filter;

        this.revealAllCards = false;
        this.numberCardsToReveal = numberCardsToReveal;
        this.numberCardsToDiscard = numberCardsToDiscard;

        staticText = this.setText();
    }

    public DiscardCardYouChooseTargetEffect(final DiscardCardYouChooseTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
        this.numberCardsToDiscard = effect.numberCardsToDiscard;
        this.numberCardsToReveal = effect.numberCardsToReveal;
        this.revealAllCards = effect.revealAllCards;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        if (revealAllCards) {
            this.numberCardsToReveal = StaticValue.get(player.getHand().size());
        }
        int numberToReveal = this.numberCardsToReveal.calculate(game, source, this);
        if (numberToReveal <= 0) {
            return true;
        }
        Cards revealedCards = new CardsImpl();
        numberToReveal = Math.min(player.getHand().size(), numberToReveal);
        if (player.getHand().size() > numberToReveal) {
            TargetCard chosenCards = new TargetCard(numberToReveal, numberToReveal,
                    Zone.HAND, new FilterCard("card in " + player.getName() + "'s hand"));
            chosenCards.setNotTarget(true);
            if (chosenCards.canChoose(player.getId(), source, game)
                    && player.chooseTarget(Outcome.Discard, player.getHand(), chosenCards, source, game)) {
                if (!chosenCards.getTargets().isEmpty()) {
                    List<UUID> targets = chosenCards.getTargets();
                    for (UUID targetid : targets) {
                        Card card = game.getCard(targetid);
                        if (card != null) {
                            revealedCards.add(card);
                        }
                    }
                }
            }
        } else {
            revealedCards.addAll(player.getHand());
        }

        Card sourceCard = game.getCard(source.getSourceId());
        player.revealCards(sourceCard != null ? sourceCard.getIdName() + " ("
                + sourceCard.getZoneChangeCounter(game) + ')' : "Discard", revealedCards, game);

        boolean result = true;
        int filteredCardsCount = revealedCards.count(filter, source.getControllerId(), source, game);
        int numberToDiscard = Math.min(this.numberCardsToDiscard.calculate(game, source, this), filteredCardsCount);
        if (numberToDiscard <= 0) {
            return result;
        }
        TargetCard target = new TargetCard(numberToDiscard, Zone.HAND, filter);
        if (!controller.choose(Outcome.Benefit, revealedCards, target, game)) {
            return result;
        }
        result = !player.discard(new CardsImpl(target.getTargets()), false, source, game).isEmpty();
        return result;
    }

    @Override
    public DiscardCardYouChooseTargetEffect copy() {
        return new DiscardCardYouChooseTargetEffect(this);
    }

    private String setText() {
        boolean discardMultipleCards = !numberCardsToDiscard.toString().equals("1");
        StringBuilder sb = new StringBuilder("target ");
        switch (targetController) {
            case OPPONENT:
                sb.append("opponent");
                break;
            case ANY:
                sb.append("player");
                break;
            default:
                throw new UnsupportedOperationException("target controller not supported");
        }
        sb.append(" reveals ");
        if (revealAllCards) {
            sb.append("their hand. You choose ");
            if (discardMultipleCards) {
                sb.append(numberCardsToDiscard).append(' ').append(filter.getMessage());
            } else {
                sb.append(CardUtil.addArticle(filter.getMessage()));
            }
            if (!filter.getMessage().contains("from it")) {
                sb.append(" from it");
            }
        } else {
            if (numberCardsToReveal instanceof StaticValue) {
                sb.append(CardUtil.numberToText(((StaticValue) numberCardsToReveal).getValue()));
                sb.append(" cards from their hand");
            } else if (numberCardsToReveal.getMessage().isEmpty()) {
                sb.append("X cards from their hand");
            } else {
                sb.append("a number of cards from their hand equal to ");
                sb.append(numberCardsToReveal.getMessage());
            }
            sb.append(". You choose ");
            if (numberCardsToDiscard instanceof StaticValue) {
                sb.append(CardUtil.numberToText(((StaticValue) numberCardsToDiscard).getValue()));
            } else {
                sb.append(numberCardsToDiscard);
            }
            sb.append(" of them");
        }

        sb.append(". That player discards ").append(discardMultipleCards ? "those cards" : "that card");
        return sb.toString();
    }
}
