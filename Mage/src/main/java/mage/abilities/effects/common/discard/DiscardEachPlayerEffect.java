package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiscardEachPlayerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean randomDiscard;
    private TargetController targetController;

    public DiscardEachPlayerEffect() {
        this(StaticValue.get(1), false);
    }

    public DiscardEachPlayerEffect(TargetController targetController) {
        this(StaticValue.get(1), false, targetController);
    }

    public DiscardEachPlayerEffect(int amount, boolean randomDiscard) {
        this(StaticValue.get(amount), randomDiscard);
    }

    public DiscardEachPlayerEffect(DynamicValue amount, boolean randomDiscard) {
        this(amount, randomDiscard, TargetController.ANY);
    }

    public DiscardEachPlayerEffect(DynamicValue amount, boolean randomDiscard, TargetController targetController) {
        super(Outcome.Discard);
        this.randomDiscard = randomDiscard;
        this.amount = amount;
        this.targetController = targetController;
    }

    public DiscardEachPlayerEffect(final DiscardEachPlayerEffect effect) {
        super(effect);
        this.randomDiscard = effect.randomDiscard;
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // Store for each player the cards to discard, that's important because all discard shall happen at the same time
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        if (controller == null) {
            return true;
        }
        int toDiscard = amount.calculate(game, source, this);
        // choose cards to discard
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            switch (targetController) {
                case NOT_YOU:
                    if (playerId.equals(source.getControllerId())) {
                        continue;
                    }
                    break;
                case OPPONENT:
                    if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                        continue;
                    }
                    break;
            }
            if (randomDiscard) {
                player.discard(toDiscard, true, false, source, game);
                continue;
            }
            int numberOfCardsToDiscard = Math.min(toDiscard, player.getHand().size());
            Cards cards = new CardsImpl();
            if (numberOfCardsToDiscard > 0) {
                Target target = new TargetDiscard(
                        numberOfCardsToDiscard, numberOfCardsToDiscard,
                        StaticFilters.FILTER_CARD, playerId
                );
                player.chooseTarget(outcome, target, source, game);
                cards.addAll(target.getTargets());
            }
            cardsToDiscard.put(playerId, cards);
        }
        if (randomDiscard) {
            return true;
        }
        // discard all chosen cards
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.discard(cardsToDiscard.get(playerId), false, source, game);
        }
        return true;
    }

    @Override
    public DiscardEachPlayerEffect copy() {
        return new DiscardEachPlayerEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("each ");
        switch (targetController) {
            case NOT_YOU:
                sb.append("other player");
                break;
            case OPPONENT:
                sb.append("opponent");
                break;
            case ANY:
                sb.append("player");
                break;
        }
        sb.append(" discards ");
        sb.append(CardUtil.numberToText(amount.toString(), "a")).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append('s');
            }
        } catch (Exception e) {
            sb.append('s');
        }
        if (randomDiscard) {
            sb.append(" at random");
        }
        return sb.toString();
    }
}
