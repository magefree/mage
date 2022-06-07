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

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlTargetEffect extends OneShotEffect {

    private boolean tapped;
    protected boolean returnFromExileZoneOnly;
    private String returnName = "that card";
    private String returnUnderControlName = "its owner's";

    /**
     * @param returnFromExileZoneOnly see https://github.com/magefree/mage/issues/5151
     *                                return it or that card - false
     *                                return exiled card - true
     */
    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean returnFromExileZoneOnly) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.returnFromExileZoneOnly = returnFromExileZoneOnly;

        updateText();
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(final ReturnToBattlefieldUnderOwnerControlTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.returnFromExileZoneOnly = effect.returnFromExileZoneOnly;
        this.returnName = effect.returnName;
        this.returnUnderControlName = effect.returnUnderControlName;

        updateText();
    }

    private void updateText() {
        this.staticText = ", then return " + this.returnName
                + " to the battlefield" + (tapped ? " tapped" : "")
                + " under " + this.returnUnderControlName + " control";
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlTargetEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToBattlefield = new CardsImpl();
            if (returnFromExileZoneOnly) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    if (game.getExile().containsId(targetId, game)) {
                        cardsToBattlefield.add(targetId);
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
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, tapped, false, true, null);
            }
            return true;
        }
        return false;
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect withReturnNames(String returnName, String returnUnderControlName) {
        this.returnName = returnName;
        this.returnUnderControlName = returnUnderControlName;
        updateText();
        return this;
    }
}
