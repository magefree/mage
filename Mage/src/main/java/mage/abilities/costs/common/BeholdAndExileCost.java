package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.constants.BeholdType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class BeholdAndExileCost extends CostImpl {

    private final BeholdType beholdType;

    public BeholdAndExileCost(BeholdType beholdType) {
        super();
        this.beholdType = beholdType;
        this.text = "behold " + beholdType.getDescription() + " and exile it";
    }

    private BeholdAndExileCost(final BeholdAndExileCost cost) {
        super(cost);
        this.beholdType = cost.beholdType;
    }

    @Override
    public BeholdAndExileCost copy() {
        return new BeholdAndExileCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return beholdType.canBehold(controllerId, 1, game, source);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        Card card = beholdType.beholdOne(player, game, source);
        if (card == null) {
            paid = false;
            return paid;
        }
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source, 1),
                CardUtil.getSourceName(game, source)
        );
        paid = true;
        return paid;
    }
}
