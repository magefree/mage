package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlTargetEffect extends OneShotEffect {

    private boolean returnFromExileZoneOnly;

    public ReturnToBattlefieldUnderYourControlTargetEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean returnFromExileZoneOnly) {
        this(returnFromExileZoneOnly, "that card");
    }

    /**
     * @param returnFromExileZoneOnly see https://github.com/magefree/mage/issues/5151
     *                                return it or that card - false
     *                                return exiled card - true
     */
    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean returnFromExileZoneOnly, String description) {
        super(Outcome.Benefit);
        this.returnFromExileZoneOnly = returnFromExileZoneOnly;
        staticText = "return " + description + " to the battlefield under your control";
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(final ReturnToBattlefieldUnderYourControlTargetEffect effect) {
        super(effect);
        this.returnFromExileZoneOnly = effect.returnFromExileZoneOnly;
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
            if (returnFromExileZoneOnly) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    UUID mainCardId = CardUtil.getMainCardId(game, targetId);
                    if (game.getExile().containsId(mainCardId, game)) {
                        cardsToBattlefield.add(mainCardId);
                    } else {
                        Card card = game.getCard(targetId);
                        if (card instanceof MeldCard) {
                            MeldCard meldCard = (MeldCard) card;
                            Card topCard = meldCard.getTopHalfCard();
                            Card bottomCard = meldCard.getBottomHalfCard();
                            if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && game.getExile().containsId(topCard.getId(), game)) {
                                cardsToBattlefield.add(topCard);
                            }
                            if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && game.getExile().containsId(bottomCard.getId(), game)) {
                                cardsToBattlefield.add(bottomCard);
                            }
                        }
                    }
                }
            } else {
                cardsToBattlefield.addAll(getTargetPointer().getTargets(game, source));
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
            }
            return true;
        }
        return false;
    }
}
