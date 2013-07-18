package mage.abilities.effects.common;

import java.util.HashMap;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;

public class DiscardEachPlayerEffect extends OneShotEffect<DiscardEachPlayerEffect> {

    protected DynamicValue amount;
    protected boolean randomDiscard;

    public DiscardEachPlayerEffect() {
        this(new StaticValue(1), false);
    }

    public DiscardEachPlayerEffect(int amount, boolean randomDiscard) {
        this(new StaticValue(amount), randomDiscard);
    }

    public DiscardEachPlayerEffect(DynamicValue amount, boolean randomDiscard) {
        super(Outcome.Discard);
        this.randomDiscard = randomDiscard;
        this.amount = amount;
    }

    public DiscardEachPlayerEffect(final DiscardEachPlayerEffect effect) {
        super(effect);
        this.randomDiscard = effect.randomDiscard;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        HashMap<UUID, Cards> cardsToDiscard = new HashMap<UUID, Cards>();
        if (controller != null) {
            // choose cards to discard
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                     int numberOfCardsToDiscard = Math.min(amount.calculate(game, source), player.getHand().size());
                     Cards cards = new CardsImpl();
                     if (randomDiscard) {
                         while (cards.size() < numberOfCardsToDiscard) {
                             Card card = player.getHand().getRandom(game);
                             if (!cards.contains(card.getId())) {
                                 cards.add(card);
                             }
                         }
                     } else {
                         Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, new FilterCard(), playerId);
                         target.setRequired(true);
                         player.chooseTarget(outcome, target, source, game);
                         cards.addAll(target.getTargets());
                     }
                     cardsToDiscard.put(playerId, cards);
                }
            }
            // discard all choosen cards
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null) {
                        for (UUID cardId : cardsPlayer) {
                            Card card = game.getCard(cardId);
                            if (card != null) {
                                player.discard(card, source, game);
                            }
                        }
                        game.informPlayers(new StringBuilder(player.getName()).append(" discards ").append(Integer.toString(cardsPlayer.size())).append(" card").append(cardsPlayer.size() > 1?"s":"").toString());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public DiscardEachPlayerEffect copy() {
        return new DiscardEachPlayerEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Each player discards ");
        sb.append(CardUtil.numberToText(amount.toString())).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append("s");
            }
        } catch (Exception e) {
            sb.append("s");
        }
        if (randomDiscard) {
            sb.append(" at random");
        }
        return sb.toString();
    }

}
