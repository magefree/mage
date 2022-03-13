
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public final class BenefactionOfRhonas extends CardImpl {

    public BenefactionOfRhonas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top five cards of your library. You may put a creature card and/or enchantment card from among them into your hand. Put the rest into your graveyard.
        getSpellAbility().addEffect(new BenefactionOfRhonasEffect());
    }

    private BenefactionOfRhonas(final BenefactionOfRhonas card) {
        super(card);
    }

    @Override
    public BenefactionOfRhonas copy() {
        return new BenefactionOfRhonas(this);
    }
}

class BenefactionOfRhonasEffect extends OneShotEffect {

    public BenefactionOfRhonasEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature card and/or enchantment card from among them into your hand. Put the rest into your graveyard";
    }

    public BenefactionOfRhonasEffect(final BenefactionOfRhonasEffect effect) {
        super(effect);
    }

    @Override
    public BenefactionOfRhonasEffect copy() {
        return new BenefactionOfRhonasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            boolean creatureCardFound = false;
            boolean enchantmentCardFound = false;
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    cards.add(card);
                    if (card.isCreature(game)) {
                        creatureCardFound = true;
                    }
                    if (card.isEnchantment(game)) {
                        enchantmentCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                if ((creatureCardFound || enchantmentCardFound)
                        && controller.chooseUse(Outcome.DrawCard,
                                "Put a creature card and/or enchantment card into your hand?", source, game)) {
                    TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to put into your hand"));
                    if (creatureCardFound && controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }

                    target = new TargetCard(Zone.LIBRARY, new FilterEnchantmentCard("enchantment card to put into your hand"));
                    if (enchantmentCardFound && controller.chooseTarget(Outcome.DrawCard, cards, target, source, game)) {
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                }
            }
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}
