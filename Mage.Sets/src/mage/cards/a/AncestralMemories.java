
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public final class AncestralMemories extends CardImpl {

    public AncestralMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}{U}");

        // Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(7), false, new StaticValue(2),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false));
    }

    public AncestralMemories(final AncestralMemories card) {
        super(card);
    }

    @Override
    public AncestralMemories copy() {
        return new AncestralMemories(this);
    }
}

class AncestralMemoriesEffect extends OneShotEffect {

    public AncestralMemoriesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top seven cards of your library. Put two of them into your hand and the rest into your graveyard";
    }

    public AncestralMemoriesEffect(final AncestralMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public AncestralMemoriesEffect copy() {
        return new AncestralMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
            if (!cards.isEmpty()) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.LIBRARY, new FilterCard("two cards to put in your hand"));
                if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Cards toHand = new CardsImpl(target.getTargets());
                    controller.moveCards(cards, Zone.HAND, source, game);
                    cards.removeAll(toHand);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
