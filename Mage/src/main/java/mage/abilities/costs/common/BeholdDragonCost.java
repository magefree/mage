package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class BeholdDragonCost extends CostImpl {

    private static final FilterPermanent filterPermanent = new FilterControlledPermanent(SubType.DRAGON);
    private static final FilterCard filterCard = new FilterCard("a Dragon card");

    static {
        filterCard.add(SubType.DRAGON.getPredicate());
    }

    public BeholdDragonCost() {
        super();
        this.text = "behold a Dragon";
    }

    private BeholdDragonCost(final BeholdDragonCost cost) {
        super(cost);
    }

    @Override
    public BeholdDragonCost copy() {
        return new BeholdDragonCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(game.getPlayer(controllerId))
                .map(Player::getHand)
                .map(cards -> cards.count(filterCard, game) > 0)
                .orElse(false)
                || game
                .getBattlefield()
                .contains(filterPermanent, controllerId, source, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            paid = false;
            return paid;
        }
        boolean hasPermanent = game
                .getBattlefield()
                .contains(filterPermanent, controllerId, source, game, 1);
        boolean hasHand = player.getHand().count(filterCard, game) > 0;
        boolean usePermanent;
        if (hasPermanent && hasHand) {
            usePermanent = player.chooseUse(
                    Outcome.Neutral, "Choose a Dragon you control or reveal one from your hand?",
                    null, "Choose controlled", "Reveal from hand", source, game);
        } else if (hasPermanent) {
            usePermanent = true;
        } else if (hasHand) {
            usePermanent = false;
        } else {
            paid = false;
            return paid;
        }
        if (usePermanent) {
            TargetPermanent target = new TargetPermanent(filterPermanent);
            target.withNotTarget(true);
            player.choose(Outcome.Neutral, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                paid = false;
                return paid;
            }
            game.informPlayers(player.getLogName() + " chooses to behold " + permanent.getLogName());
            paid = true;
            return true;
        }
        TargetCard target = new TargetCardInHand(filterCard);
        player.choose(Outcome.Neutral, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            paid = false;
            return paid;
        }
        player.revealCards(source, new CardsImpl(card), game);
        paid = true;
        return paid;
    }
}
