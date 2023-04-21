package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RevivingVapors extends CardImpl {

    public RevivingVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");

        // Reveal the top three cards of your library and put one of them into your hand. You gain life equal to that card's converted mana cost. Put all other cards revealed this way into your graveyard.
        this.getSpellAbility().addEffect(new RevivingVaporsEffect());
    }

    private RevivingVapors(final RevivingVapors card) {
        super(card);
    }

    @Override
    public RevivingVapors copy() {
        return new RevivingVapors(this);
    }
}

class RevivingVaporsEffect extends OneShotEffect {

    public RevivingVaporsEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top three cards of your library and put one of them into your hand. You gain life equal to that card's mana value. Put all other cards revealed this way into your graveyard";
    }

    public RevivingVaporsEffect(final RevivingVaporsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));
        if (!cards.isEmpty()) {
            // Reveal the top three cards of your library and put one of them into your hand
            controller.revealCards(sourceObject.getName(), cards, game);
            Card card = null;
            if (cards.size() == 1) {
                card = cards.getRandom(game);
            } else {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put into your hand"));
                target.setNotTarget(true);
                target.setRequired(true);
                if (controller.choose(Outcome.DrawCard, cards, target, source, game)) {
                    card = cards.get(target.getFirstTarget(), game);
                }
            }
            if (card != null) {
                cards.remove(card);
                controller.moveCards(card, Zone.HAND, source, game);

                // You gain life equal to that card's converted mana cost
                controller.gainLife(card.getManaValue(), game, source);
            }

            // Put all other cards revealed this way into your graveyard
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public RevivingVaporsEffect copy() {
        return new RevivingVaporsEffect(this);
    }
}
