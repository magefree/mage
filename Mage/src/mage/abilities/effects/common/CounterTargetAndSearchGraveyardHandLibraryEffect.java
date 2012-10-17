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

package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */

public class CounterTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {


    public CounterTargetAndSearchGraveyardHandLibraryEffect() {
        this(false,"its controller's", "all cards with the same name as that spell" );
    }

    public CounterTargetAndSearchGraveyardHandLibraryEffect(Boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(graveyardExileOptional, searchWhatText, searchForText);
    }

    public CounterTargetAndSearchGraveyardHandLibraryEffect(final CounterTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
    }

    @Override
    public CounterTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new CounterTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        String cardName = "";
        UUID searchPlayerId = null;

        if (source.getTargets().get(0) instanceof TargetSpell) {
            UUID objectId = source.getFirstTarget();
            StackObject stackObject = game.getStack().getStackObject(objectId);
            if (stackObject != null) {
                MageObject targetObject = game.getObject(stackObject.getSourceId());
                if (targetObject instanceof Card) {
                    cardName = targetObject.getName();
                }
                searchPlayerId = stackObject.getControllerId();
                result = game.getStack().counter(objectId, source.getSourceId(), game);
            }
        }
        // 5/1/2008: If the targeted spell can't be countered (it's Vexing Shusher, for example),
        // that spell will remain on the stack. Counterbore will continue to resolve. You still
        // get to search for and exile all other cards with that name.
        this.applySearchAndExile(game, source, cardName, searchPlayerId);

        return result;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Counter target ").append(mode.getTargets().get(0).getFilter().getMessage()).append(". ");
        sb.append(super.getText(mode));
        return sb.toString();
    }
}
