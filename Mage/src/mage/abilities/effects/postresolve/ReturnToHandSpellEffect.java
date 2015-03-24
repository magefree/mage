package mage.abilities.effects.postresolve;

import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.PostResolveEffect;
import mage.cards.Card;
import mage.game.Game;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class ReturnToHandSpellEffect extends PostResolveEffect implements MageSingleton {
    private static final ReturnToHandSpellEffect fINSTANCE =  new ReturnToHandSpellEffect();

        private Object readResolve() throws ObjectStreamException {
            return fINSTANCE;
        }

        private ReturnToHandSpellEffect() {
            staticText = "Return {this} to its owner's hand";
        }

        public static ReturnToHandSpellEffect getInstance() {
            return fINSTANCE;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public ReturnToHandSpellEffect copy() {
            return fINSTANCE;
        }

    @Override
    public void postResolve(Card card, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.STACK);
        }
    }
}

