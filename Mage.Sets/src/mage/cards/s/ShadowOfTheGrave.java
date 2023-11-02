
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsCycledOrDiscardedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class ShadowOfTheGrave extends CardImpl {

    public ShadowOfTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Return to your hand all cards in your graveyard that you cycled or discarded this turn.
        this.getSpellAbility().addEffect(new ShadowOfTheGraveEffect());
        this.getSpellAbility().addWatcher(new CardsCycledOrDiscardedThisTurnWatcher());

    }

    private ShadowOfTheGrave(final ShadowOfTheGrave card) {
        super(card);
    }

    @Override
    public ShadowOfTheGrave copy() {
        return new ShadowOfTheGrave(this);
    }
}

class ShadowOfTheGraveEffect extends OneShotEffect {

    public ShadowOfTheGraveEffect() {
        super(Outcome.Benefit);
        staticText = "Return to your hand all cards in your graveyard that you cycled or discarded this turn";
    }

    private ShadowOfTheGraveEffect(final ShadowOfTheGraveEffect effect) {
        super(effect);
    }

    @Override
    public ShadowOfTheGraveEffect copy() {
        return new ShadowOfTheGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsCycledOrDiscardedThisTurnWatcher watcher = game.getState().getWatcher(CardsCycledOrDiscardedThisTurnWatcher.class);
        if (controller != null
                && watcher != null) {
            for (Card card : watcher.getCardsCycledOrDiscardedThisTurn(controller.getId()).getCards(game)) {
                if (game.getState().getZone(card.getId()) == Zone.GRAVEYARD //must come from their graveyard
                        && card.isOwnedBy(controller.getId())) {  //confirm ownership, but it should not be possible to get not ownwd cards here
                    controller.moveCardToHandWithInfo(card, source, game, true);
                }
            }
            return true;
        }
        return false;
    }
}
