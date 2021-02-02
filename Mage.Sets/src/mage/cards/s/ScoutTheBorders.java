
package mage.cards.s;

import java.util.UUID;
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
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class ScoutTheBorders extends CardImpl {

    public ScoutTheBorders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new ScoutTheBordersEffect());
    }

    private ScoutTheBorders(final ScoutTheBorders card) {
        super(card);
    }

    @Override
    public ScoutTheBorders copy() {
        return new ScoutTheBorders(this);
    }
}

class ScoutTheBordersEffect extends OneShotEffect {

    private static final FilterCard filterPutInHand = new FilterCard("creature or land card to put in hand");

    static {
        filterPutInHand.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public ScoutTheBordersEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard";
    }

    public ScoutTheBordersEffect(final ScoutTheBordersEffect effect) {
        super(effect);
    }

    @Override
    public ScoutTheBordersEffect copy() {
        return new ScoutTheBordersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            boolean properCardFound = cards.count(filterPutInHand, game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(source, cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutInHand);
                if (properCardFound && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cards.remove(card);
                    }

                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
