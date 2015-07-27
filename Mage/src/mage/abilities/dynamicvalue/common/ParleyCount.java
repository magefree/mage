/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.dynamicvalue.common;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * Don't use this for continuous effects because it applies a reveal effect!
 *
 * @author LevelX2
 */
public class ParleyCount implements DynamicValue, MageSingleton {

    private static final ParleyCount fINSTANCE = new ParleyCount();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static ParleyCount getInstance() {
        return fINSTANCE;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        // Each player reveals the top card of his or her library. For each nonland card revealed this way
        int parleyValue = 0;
        MageObject sourceObject = game.getObject(sourceAbility.getSourceId());
        if (sourceObject != null) {
            for (UUID playerId : game.getState().getPlayerList(sourceAbility.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Card card = player.getLibrary().getFromTop(game);
                    if (card != null) {
                        if (!card.getCardType().contains(CardType.LAND)) {
                            parleyValue++;
                        }
                        player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ")", new CardsImpl(card), game);
                    }
                }

            }
        }
        return parleyValue;
    }

    @Override
    public DynamicValue copy() {
        return fINSTANCE;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Parley";
    }

}
