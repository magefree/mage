package mage.abilities.effects.common;

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

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlTargetEffect extends OneShotEffect {

    private boolean tapped;
    protected boolean fromExileZone;
    private String returnName = "that card";
    private String returnUnderControlName = "its owner's";

    public ReturnToBattlefieldUnderOwnerControlTargetEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped) {
        this(tapped, false);
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean fromExileZone) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.fromExileZone = fromExileZone;

        updateText();
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(final ReturnToBattlefieldUnderOwnerControlTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.fromExileZone = effect.fromExileZone;
        this.returnName = effect.returnName;
        this.returnUnderControlName = effect.returnUnderControlName;

        updateText();
    }

    private void updateText() {
        this.staticText = "return " + this.returnName
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
            if (fromExileZone) {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (exileZoneId != null) {
                    ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                    if (exileZone != null) {
                        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                            if (exileZone.contains(targetId)) {
                                cardsToBattlefield.add(targetId);
                            } else {
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
