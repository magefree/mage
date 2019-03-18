
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlTargetEffect extends OneShotEffect {

    private boolean fromExileZone;

    public ReturnToBattlefieldUnderYourControlTargetEffect() {
        this(false);
    }

    /**
     *
     * @param fromExileZone - the card will only be returned if it's still in
     * the source object specific exile zone
     */
    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean fromExileZone) {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under your control";
        this.fromExileZone = fromExileZone;
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(final ReturnToBattlefieldUnderYourControlTargetEffect effect) {
        super(effect);
        this.fromExileZone = effect.fromExileZone;
    }

    @Override
    public ReturnToBattlefieldUnderYourControlTargetEffect copy() {
        return new ReturnToBattlefieldUnderYourControlTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToBattlefield = new CardsImpl();
            if (fromExileZone) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exileZoneId != null) {
                    ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                    if (exileZone != null) {
                        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                            if (exileZone.contains(targetId)) {
                                cardsToBattlefield.add(targetId);
                            }
                            else {
                                Card card = game.getCard(targetId);
                                if (card instanceof MeldCard) {
                                    MeldCard meldCard = (MeldCard) card;
                                    Card topCard = meldCard.getTopHalfCard();
                                    Card bottomCard = meldCard.getBottomHalfCard();
                                    if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && exileZone.contains(topCard.getId())) {
                                        cardsToBattlefield.add(topCard);
                                    }
                                    if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && exileZone.contains(bottomCard.getId())) {
                                        cardsToBattlefield.add(bottomCard);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                cardsToBattlefield.addAll(getTargetPointer().getTargets(game, source));
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
