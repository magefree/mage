
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class IntoTheWilds extends CardImpl {

    public IntoTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your upkeep, look at the top card of your library. If it's a land card, you may put it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new IntoTheWildsEffect(), TargetController.YOU, false));

    }

    private IntoTheWilds(final IntoTheWilds card) {
        super(card);
    }

    @Override
    public IntoTheWilds copy() {
        return new IntoTheWilds(this);
    }
}

class IntoTheWildsEffect extends OneShotEffect {

    public IntoTheWildsEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield";
    }

    private IntoTheWildsEffect(final IntoTheWildsEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheWildsEffect copy() {
        return new IntoTheWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.lookAtCards(source, "", new CardsImpl(card), game);
            if (card.isLand(game)) {
                String message = "Put " + card.getName() + " onto the battlefield?";
                if (controller.chooseUse(outcome, message, source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return true;
    }
}
