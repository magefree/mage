
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class TeleminPerformance extends CardImpl {

    public TeleminPerformance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Target opponent reveals cards from the top of their library until they reveal a creature card. That player puts all noncreature cards revealed this way into their graveyard, then you put the creature card onto the battlefield under your control.
        this.getSpellAbility().addEffect(new TeleminPerformanceEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private TeleminPerformance(final TeleminPerformance card) {
        super(card);
    }

    @Override
    public TeleminPerformance copy() {
        return new TeleminPerformance(this);
    }
}

class TeleminPerformanceEffect extends OneShotEffect {

    public TeleminPerformanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target opponent reveals cards from the top of their library until they reveal a creature card. That player puts all noncreature cards revealed this way into their graveyard, then you put the creature card onto the battlefield under your control";
    }

    private TeleminPerformanceEffect(final TeleminPerformanceEffect effect) {
        super(effect);
    }

    @Override
    public TeleminPerformanceEffect copy() {
        return new TeleminPerformanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (opponent != null) {
                Card creature = null;
                CardsImpl nonCreatures = new CardsImpl();
                CardsImpl reveal = new CardsImpl();
                for (Card card : opponent.getLibrary().getCards(game)) {
                    reveal.add(card);
                    if (card.isCreature(game)) {
                        creature = card;
                        break;
                    } else {
                        nonCreatures.add(card);
                    }
                }
                opponent.revealCards(source, reveal, game);
                opponent.moveCards(nonCreatures, Zone.GRAVEYARD, source, game);
                if (creature != null) {
                    game.getState().processAction(game);
                    controller.moveCards(creature, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
