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

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlTargetEffect extends OneShotEffect {

    private final boolean tapped;
    private final boolean returnFromExileZoneOnly;

    /**
     * @param returnFromExileZoneOnly see https://github.com/magefree/mage/issues/5151
     *                                return it or that card - false
     *                                return exiled card - true
     */
    public ReturnToBattlefieldUnderOwnerControlTargetEffect(boolean tapped, boolean returnFromExileZoneOnly) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.returnFromExileZoneOnly = returnFromExileZoneOnly;
        staticText = "return that card to the battlefield " + (tapped ? "tapped " : "") + "under its owner's control";
    }

    protected ReturnToBattlefieldUnderOwnerControlTargetEffect(final ReturnToBattlefieldUnderOwnerControlTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.returnFromExileZoneOnly = effect.returnFromExileZoneOnly;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlTargetEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlTargetEffect(this);
    }

    protected Cards getCardsToReturn(Game game, Ability source) {
        Cards cardsToBattlefield = new CardsImpl();
        if (returnFromExileZoneOnly) {
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Permanent lkiPermanent = game.getPermanentOrLKIBattlefield(targetId);
                if (game.getExile().containsId(targetId, game)) {
                    cardsToBattlefield.add(targetId);
                    if (lkiPermanent != null && lkiPermanent.getMainCard().getMeldedWith(game) != null) {
                        // check for meld
                        cardsToBattlefield.add(lkiPermanent.getMainCard().getMeldedWith(game));
                    }
                }
            }
        } else {
            cardsToBattlefield.addAll(getTargetPointer().getTargets(game, source));
        }
        return cardsToBattlefield;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cardsToBattlefield = getCardsToReturn(game, source);
        if (!cardsToBattlefield.isEmpty()) {
            controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, tapped, false, true, null);
        }
        return true;
    }
}
