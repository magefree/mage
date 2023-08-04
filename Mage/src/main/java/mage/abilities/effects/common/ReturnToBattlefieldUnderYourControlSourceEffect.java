
package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlSourceEffect extends OneShotEffect {

    public ReturnToBattlefieldUnderYourControlSourceEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under your control";
    }

    protected ReturnToBattlefieldUnderYourControlSourceEffect(final ReturnToBattlefieldUnderYourControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToBattlefieldUnderYourControlSourceEffect copy() {
        return new ReturnToBattlefieldUnderYourControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
        if (exileZone != null && exileZone.contains(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null
                    && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                return true;
            }
        }
        return false;
    }

}
