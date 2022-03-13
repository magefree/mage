package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RofellossGift extends CardImpl {

    public RofellossGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Reveal any number of green cards in your hand. Return an enchantment card from your graveyard to your hand for each card revealed this way.
        this.getSpellAbility().addEffect(new RofellossGiftEffect());
    }

    private RofellossGift(final RofellossGift card) {
        super(card);
    }

    @Override
    public RofellossGift copy() {
        return new RofellossGift(this);
    }
}

class RofellossGiftEffect extends OneShotEffect {

    public static final FilterCard filter1 = new FilterCard("green cards in your hand");
    public static final FilterCard filter2 = new FilterCard("enchantment cards in your graveyard");

    static {
        filter1.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(CardType.ENCHANTMENT.getPredicate());
    }

    public RofellossGiftEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal any number of green cards in your hand. " +
                "Return an enchantment card from your graveyard to your hand for each card revealed this way.";
    }

    public RofellossGiftEffect(final RofellossGiftEffect effect) {
        super(effect);
    }

    @Override
    public RofellossGiftEffect copy() {
        return new RofellossGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filter1);
        if (!player.choose(outcome, player.getHand(), targetCardInHand, game)) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID cardId : targetCardInHand.getTargets()) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.add(card);
            }
        }
        player.revealCards(source, cards, game);
        int enchantmentsToReturn = Math.min(player.getGraveyard().count(filter2, game), targetCardInHand.getTargets().size());
        TargetCardInYourGraveyard targetCardInYourGraveyard = new TargetCardInYourGraveyard(enchantmentsToReturn, filter2);
        targetCardInYourGraveyard.setNotTarget(true);
        if (!player.choose(outcome, targetCardInYourGraveyard, source, game)) {
            return false;
        }
        cards = new CardsImpl();
        for (UUID cardId : targetCardInYourGraveyard.getTargets()) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.add(card);
            }
        }
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
