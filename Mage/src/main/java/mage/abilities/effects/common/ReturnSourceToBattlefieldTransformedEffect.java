package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class ReturnSourceToBattlefieldTransformedEffect extends OneShotEffect {

    private final boolean ownersControl;

    public ReturnSourceToBattlefieldTransformedEffect(boolean ownersControl) {
        super(Outcome.PutCreatureInPlay);
        staticText = "return it to the battlefield transformed under " + (ownersControl ? "its owner's" : "your") + " control";
        this.ownersControl = ownersControl;
    }

    private ReturnSourceToBattlefieldTransformedEffect(final ReturnSourceToBattlefieldTransformedEffect effect) {
        super(effect);
        this.ownersControl = effect.ownersControl;
    }

    @Override
    public ReturnSourceToBattlefieldTransformedEffect copy() {
        return new ReturnSourceToBattlefieldTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card) || sourceObject.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        Card card = (Card) sourceObject;
        TransformingDoubleFacedCard.setCardTransformed(card, game);
        return controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, ownersControl, null);
    }
}
