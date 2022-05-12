package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.target.TargetCard;

/**
 * @author jeffwadsworth
 */
public final class ThoughtHemorrhage extends CardImpl {

    public ThoughtHemorrhage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Name a nonland card. Target player reveals their hand. Thought 
        // Hemorrhage deals 3 damage to that player for each card with that 
        // name revealed this way. Search that player's graveyard, hand, and 
        // library for all cards with that name and exile them. Then that 
        // player shuffles their library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(
                ChooseACardNameEffect.TypeOfName.NON_LAND_NAME));
        this.getSpellAbility().addEffect(new ThoughtHemorrhageEffect());
    }

    private ThoughtHemorrhage(final ThoughtHemorrhage card) {
        super(card);
    }

    @Override
    public ThoughtHemorrhage copy() {
        return new ThoughtHemorrhage(this);
    }
}

class ThoughtHemorrhageEffect extends OneShotEffect {

    static final String rule = "Target player reveals their hand. "
            + "{this} deals 3 damage to that player for each card with "
            + "the chosen name revealed this way. "
            + "Search that player's graveyard, hand, and library for "
            + "all cards with that name and exile them. "
            + "Then that player shuffles";

    public ThoughtHemorrhageEffect() {
        super(Outcome.Exile);
        staticText = rule;
    }

    public ThoughtHemorrhageEffect(final ThoughtHemorrhageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(
                source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (sourceObject != null
                && controller != null
                && cardName != null
                && !cardName.isEmpty()) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.revealCards("hand of "
                        + targetPlayer.getName(), targetPlayer.getHand(), game);
                int cardsFound = 0;
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    if (CardUtil.haveSameNames(card, cardName, game)) {
                        cardsFound++;
                    }
                }
                if (cardsFound > 0) {
                    targetPlayer.damage(3 * cardsFound, source.getSourceId(), source, game);
                }
                // Exile all cards with the same name
                // Building a card filter with the name
                FilterCard filterNamedCards = new FilterCard();
                filterNamedCards.add(new NamePredicate(cardName));

                Set<Card> toExile = new LinkedHashSet<>();
                // The cards you're searching for must be found and exiled if 
                // they're in the graveyard because it's a public zone.
                // Finding those cards in the hand and library is optional, 
                // because those zones are hidden (even if the hand is temporarily revealed).
                // search cards in graveyard
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(cardName)) {
                        toExile.add(checkCard);
                    }
                }

                // search cards in Hand
                TargetCard targetCardInHand = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCards);
                if (controller.chooseTarget(Outcome.Exile, targetPlayer.getHand(), targetCardInHand, source, game)) {
                    List<UUID> targets = targetCardInHand.getTargets();
                    for (UUID targetId : targets) {
                        Card targetCard = targetPlayer.getHand().get(targetId, game);
                        if (targetCard != null) {
                            toExile.add(targetCard);
                        }
                    }
                }

                // search cards in Library
                // If the player has no nonland cards in their hand, you can still search 
                // that player's library and have that player shuffle it.
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, source, game, targetPlayer.getId());
                for (UUID cardId : targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        toExile.add(card);
                    }
                }
                controller.moveCards(toExile, Zone.EXILED, source, game);
                targetPlayer.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public ThoughtHemorrhageEffect copy() {
        return new ThoughtHemorrhageEffect(this);
    }
}
