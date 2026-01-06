package mage.constants;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.CardsImpl;
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
public enum BeholdType {
    DRAGON(SubType.DRAGON),
    ELF(SubType.ELF),
    KITHKIN(SubType.KITHKIN),
    MERFOLK(SubType.MERFOLK);

    private final FilterPermanent filterPermanent;
    private final FilterCard filterCard;
    private final String description;

    BeholdType(SubType subType) {
        this.filterPermanent = new FilterControlledPermanent(subType);
        this.filterCard = new FilterCard(subType);
        this.description = subType.getIndefiniteArticle() + ' ' + subType.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public FilterCard getFilterCard() {
        return filterCard;
    }

    public FilterPermanent getFilterPermanent() {
        return filterPermanent;
    }

    public boolean canBehold(UUID controllerId, Game game, Ability source) {
        return Optional
                .ofNullable(game.getPlayer(controllerId))
                .map(Player::getHand)
                .map(cards -> cards.count(this.getFilterCard(), game) > 0)
                .orElse(false)
                || game
                .getBattlefield()
                .contains(this.getFilterPermanent(), controllerId, source, game, 1);
    }

    public Card doBehold(Player player, Game game, Ability source) {
        boolean hasPermanent = game
                .getBattlefield()
                .contains(this.getFilterPermanent(), player.getId(), source, game, 1);
        boolean hasHand = player.getHand().count(this.getFilterCard(), game) > 0;
        boolean usePermanent;
        if (hasPermanent && hasHand) {
            usePermanent = player.chooseUse(
                    Outcome.Neutral, "Choose " + this.getDescription() + " you control or reveal one from your hand?",
                    null, "Choose controlled", "Reveal from hand", source, game);
        } else if (hasPermanent) {
            usePermanent = true;
        } else if (hasHand) {
            usePermanent = false;
        } else {
            return null;
        }
        if (usePermanent) {
            TargetPermanent target = new TargetPermanent(this.getFilterPermanent());
            target.withNotTarget(true);
            player.choose(Outcome.Neutral, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                return null;
            }
            game.informPlayers(player.getLogName() + " chooses to behold " + permanent.getLogName());
            return permanent;
        }
        TargetCard target = new TargetCardInHand(this.getFilterCard());
        player.choose(Outcome.Neutral, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return null;
        }
        player.revealCards(source, new CardsImpl(card), game);
        return card;
    }
}
