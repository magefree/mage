package mage.abilities.costs.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.hint.HintUtils;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CollectEvidenceCost extends CostImpl {

    private final int amount;

    public CollectEvidenceCost(int amount) {
        super();
        this.amount = amount;
        this.text = "collect evidence " + amount;
    }

    private CollectEvidenceCost(final CollectEvidenceCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum() >= amount;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        // TODO: require target to have minimum selected total mana value (requires refactor)
        Target target = new TargetCardInYourGraveyard(1, Integer.MAX_VALUE) {
            @Override
            public String getMessage() {
                // shows selected mana value
                int totalMV = this
                        .getTargets()
                        .stream()
                        .map(game::getCard)
                        .filter(Objects::nonNull)
                        .mapToInt(MageObject::getManaValue)
                        .sum();
                return super.getMessage() + HintUtils.prepareText(
                        " (selected mana value " + totalMV + " of " + amount + ")",
                        totalMV >= amount ? Color.GREEN : Color.RED
                );
            }
        }.withNotTarget(true);
        player.choose(Outcome.Exile, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        paid = cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum() >= amount;
        if (paid) {
            player.moveCards(cards, Zone.EXILED, source, game);
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.EVIDENCE_COLLECTED,
                    source.getSourceId(), source, source.getControllerId(), amount
            ));
        }
        return paid;
    }

    @Override
    public CollectEvidenceCost copy() {
        return new CollectEvidenceCost(this);
    }
}
