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

    /**
     * @param returnFromExileZoneOnly see https://github.com/magefree/mage/issues/5151
     *                                return it or that card - false
     *                                return exiled card - true
     */
    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean returnFromExileZoneOnly) {
        this(tapped, returnFromExileZoneOnly, "that card");
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean returnFromExileZoneOnly, String description) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.returnFromExileZoneOnly = returnFromExileZoneOnly;
        staticText = "return " + description + " to the battlefield " + (tapped ? "tapped " : "") + "under its owner's control";
    }

    public ReturnToBattlefieldUnderOwnerControlTargetEffect(final ReturnToBattlefieldUnderOwnerControlTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.returnFromExileZoneOnly = effect.returnFromExileZoneOnly;
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
}
