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
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlTargetEffect extends OneShotEffect {

    private boolean fromExileZone;
    private boolean tapped;
    private boolean attacking;

    public ReturnToBattlefieldUnderYourControlTargetEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean fromExileZone) {
        this(fromExileZone, false, false);
    }

    /**
     * @param fromExileZone - the card will only be returned if it's still in
     *                      the source object specific exile zone
     */
    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean fromExileZone, boolean tapped, boolean attacking) {
        super(Outcome.Benefit);
        this.fromExileZone = fromExileZone;
        this.tapped = tapped;
        this.attacking = attacking;

        updateText();
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(final ReturnToBattlefieldUnderYourControlTargetEffect effect) {
        super(effect);
        this.fromExileZone = effect.fromExileZone;
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;

        updateText();
    }

    private void updateText() {
        this.staticText = "return that card to the battlefield under your control"
                + (tapped ? " tapped" : "")
                + (tapped && attacking ? " and" : "")
                + (attacking ? " attacking" : "");
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

            for (Card card : cardsToBattlefield.getCards(game)) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null)) {
                    if (attacking) {
                        game.getCombat().addAttackingCreature(card.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
