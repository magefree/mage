package mage.constants;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
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
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum BeholdType {
    DRAGON(SubType.DRAGON),
    GOBLIN(SubType.GOBLIN),
    ELEMENTAL(SubType.ELEMENTAL),
    ELF(SubType.ELF),
    KITHKIN(SubType.KITHKIN),
    MERFOLK(SubType.MERFOLK);

    private final FilterPermanent filterPermanent;
    private final FilterCard filterCard;
    private final SubType subType;
    private final String description;

    BeholdType(SubType subType) {
        this.filterPermanent = new FilterControlledPermanent(subType);
        this.filterCard = new FilterCard(subType);
        this.subType = subType;
        this.description = subType.getIndefiniteArticle() + ' ' + subType.getDescription();
    }

    public FilterCard getFilterCard() {
        return filterCard;
    }

    public FilterPermanent getFilterPermanent() {
        return filterPermanent;
    }

    public SubType getSubType() {
        return subType;
    }

    public String getDescription() {
        return description;
    }

    public boolean canBehold(UUID controllerId, int amount, Game game, Ability source) {
        return Optional
                .ofNullable(game.getPlayer(controllerId))
                .map(Player::getHand)
                .map(cards -> cards.count(this.getFilterCard(), game))
                .orElse(0)
                + game
                .getBattlefield()
                .count(this.getFilterPermanent(), controllerId, source, game) >= amount;
    }

    public Cards doBehold(Player player, int amount, Game game, Ability source) {
        if (amount == 1) {
            return new CardsImpl(beholdOne(player, game, source));
        }
        Cards cards = new CardsImpl();
        TargetPermanent targetPermanent = new TargetPermanent(0, amount, this.getFilterPermanent(), true);
        targetPermanent.withChooseHint("to behold");
        player.choose(Outcome.Neutral, targetPermanent, source, game);
        cards.addAll(targetPermanent.getTargets());
        if (cards.size() < amount) {
            TargetCard targetCard = new TargetCardInHand(
                    0, amount - cards.size(), filterCard
            ).withChooseHint("to reveal and behold");
            player.choose(Outcome.Neutral, player.getHand(), targetCard, source, game);
            cards.addAll(targetCard.getTargets());
            player.revealCards(source, new CardsImpl(targetCard.getTargets()), game);
        }
        String permMessage = CardUtil.concatWithAnd(cards
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getLogName)
                .collect(Collectors.toList()));
        String handMessage = CardUtil.concatWithAnd(cards
                .getCards(game)
                .stream()
                .filter(card -> Zone.HAND.match(game.getState().getZone(card.getId())))
                .map(MageObject::getLogName)
                .collect(Collectors.toList()));
        if (!permMessage.isEmpty() && handMessage.isEmpty()) {
            game.informPlayers(player.getLogName() + " chooses to behold " + permMessage + " from the battlefield");
        } else if (permMessage.isEmpty() && !handMessage.isEmpty()) {
            game.informPlayers(player.getLogName() + " chooses to behold " + permMessage + " from their hand");
        } else {
            game.informPlayers(player.getLogName() + " chooses to behold " + permMessage + " from the battlefield and " + permMessage + " from their hand");
        }
        return cards;
    }

    public Card beholdOne(Player player, Game game, Ability source) {
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
