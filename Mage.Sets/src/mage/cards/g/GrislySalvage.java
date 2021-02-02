
package mage.cards.g;

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
public final class GrislySalvage extends CardImpl {

    public GrislySalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new GrislySalvageEffect());
    }

    private GrislySalvage(final GrislySalvage card) {
        super(card);
    }

    @Override
    public GrislySalvage copy() {
        return new GrislySalvage(this);
    }
}

class GrislySalvageEffect extends OneShotEffect {

    private static final FilterCard filterPutInHand = new FilterCard("creature or land card to put in hand");

    static {
        filterPutInHand.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public GrislySalvageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard";
    }

    public GrislySalvageEffect(final GrislySalvageEffect effect) {
        super(effect);
    }

    @Override
    public GrislySalvageEffect copy() {
        return new GrislySalvageEffect(this);
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
                if (properCardFound && controller.chooseUse(outcome, "Put a creature or land card from the revealed cards into your hand?", source, game)
                        && controller.choose(Outcome.DrawCard, cards, target, game)) {
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
