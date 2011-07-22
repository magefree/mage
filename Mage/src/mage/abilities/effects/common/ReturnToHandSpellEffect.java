package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

import java.io.ObjectStreamException;

/**
 *
 * @author Loki
 */
public class ReturnToHandSpellEffect extends OneShotEffect<ReturnToHandSpellEffect> {
    private static final ReturnToHandSpellEffect fINSTANCE =  new ReturnToHandSpellEffect();

        private Object readResolve() throws ObjectStreamException {
            return fINSTANCE;
        }

        private ReturnToHandSpellEffect() {
            super(Constants.Outcome.Exile);
            staticText = "Return {this} into its owner's hand";
        }

        public static ReturnToHandSpellEffect getInstance() {
            return fINSTANCE;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            //this effect is applied when a spell resolves - see Spell.java
            return true;
        }

        @Override
        public ReturnToHandSpellEffect copy() {
            return fINSTANCE;
        }
    }

