package mage.abilities.effects.common;

import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.PostResolveEffect;
import mage.cards.Card;
import mage.game.Game;

import java.io.ObjectStreamException;
import java.util.UUID;

/**
 *
 * @author Loki
 */
public class ReturnToHandSpellEffect extends PostResolveEffect<ReturnToHandSpellEffect> implements MageSingleton {
    private static final ReturnToHandSpellEffect fINSTANCE =  new ReturnToHandSpellEffect();

        private Object readResolve() throws ObjectStreamException {
            return fINSTANCE;
        }

        private ReturnToHandSpellEffect() {
            staticText = "Return {this} into its owner's hand";
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
        card.moveToZone(Zone.HAND, source.getId(), game, false);
    }
}

