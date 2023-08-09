
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlSourceEffect extends OneShotEffect {

    private boolean tapped;
    private boolean attacking;
    private int zoneChangeCounter;

    public ReturnToBattlefieldUnderOwnerControlSourceEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped) {
        this(tapped, -1);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped, int zoneChangeCounter) {
        this(tapped, false, zoneChangeCounter);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped, boolean attacking, int zoneChangeCounter) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.attacking = attacking;
        this.zoneChangeCounter = zoneChangeCounter;
        staticText = "return that card to the battlefield"
                + (tapped ? " tapped" : "")
                + (attacking ? " attacking" : "")
                + " under its owner's control";
    }

    protected ReturnToBattlefieldUnderOwnerControlSourceEffect(final ReturnToBattlefieldUnderOwnerControlSourceEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.attacking = effect.attacking;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlSourceEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller != null && card != null) {
            // return only from public zones
            switch (game.getState().getZone(card.getId())) {
                case EXILED:
                case COMMAND:
                case GRAVEYARD:
                    if (zoneChangeCounter < 0 || game.getState().getZoneChangeCounter(card.getId()) == zoneChangeCounter) {

                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null)) {
                            if (attacking) {
                                game.getCombat().addAttackingCreature(card.getId(), game);
                            }
                        }
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
