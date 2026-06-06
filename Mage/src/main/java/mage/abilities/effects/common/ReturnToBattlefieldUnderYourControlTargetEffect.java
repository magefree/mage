package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noxx
 */
public class ReturnToBattlefieldUnderYourControlTargetEffect extends OneShotEffect {

    private boolean returnFromExileZoneOnly;
    private boolean tapped;

    public ReturnToBattlefieldUnderYourControlTargetEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean returnFromExileZoneOnly) {
        this(returnFromExileZoneOnly, false, "that card");
    }

    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean returnFromExileZoneOnly, boolean tapped) {
        this(returnFromExileZoneOnly, tapped, "that card");
    }

    /**
     * @param returnFromExileZoneOnly see https://github.com/magefree/mage/issues/5151
     *                                return it or that card - false
     *                                return exiled card - true
     */
    public ReturnToBattlefieldUnderYourControlTargetEffect(boolean returnFromExileZoneOnly, boolean tapped, String description) {
        super(Outcome.Benefit);
        this.returnFromExileZoneOnly = returnFromExileZoneOnly;
        this.tapped = tapped;
        staticText = "return " + description + " to the battlefield " + (tapped ? "tapped " : "") + "under your control";
    }

    protected ReturnToBattlefieldUnderYourControlTargetEffect(final ReturnToBattlefieldUnderYourControlTargetEffect effect) {
        super(effect);
        this.returnFromExileZoneOnly = effect.returnFromExileZoneOnly;
        this.tapped = effect.tapped;
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
                    Permanent lkiPermanent = game.getPermanentOrLKIBattlefield(targetId);
                    if (game.getExile().containsId(mainCardId, game)) {
                        cardsToBattlefield.add(mainCardId);
                        if (lkiPermanent != null && lkiPermanent.getMainCard().getMeldedWith(game) != null) {
                            // check for meld
                            cardsToBattlefield.add(lkiPermanent.getMainCard().getMeldedWith(game));
                        }
                    }
                }
            } else {
                cardsToBattlefield.addAll(getTargetPointer().getTargets(game, source));
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, tapped, false, false, null);
            }
            return true;
        }
        return false;
    }
}
