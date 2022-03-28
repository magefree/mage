
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class CommuneWithTheGods extends CardImpl {

    public CommuneWithTheGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top five cards of your library. You may put a creature or enchantment card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new CommuneWithTheGodsEffect());

    }

    private CommuneWithTheGods(final CommuneWithTheGods card) {
        super(card);
    }

    @Override
    public CommuneWithTheGods copy() {
        return new CommuneWithTheGods(this);
    }
}

class CommuneWithTheGodsEffect extends OneShotEffect {

    public CommuneWithTheGodsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature or enchantment card from among them into your hand. Put the rest into your graveyard";
    }

    public CommuneWithTheGodsEffect(final CommuneWithTheGodsEffect effect) {
        super(effect);
    }

    @Override
    public CommuneWithTheGodsEffect copy() {
        return new CommuneWithTheGodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            if (!cards.isEmpty()) {
                FilterCard filterPutInHand = new FilterCard("creature or enchantment card to put in hand");
                filterPutInHand.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
                controller.revealCards(source, cards, game);
                if (cards.count(filterPutInHand, source.getControllerId(), source, game) > 0) {
                    TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, filterPutInHand);
                    if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            cards.remove(card);
                            controller.moveCards(card, Zone.HAND, source, game);
                        }

                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
