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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LookLibraryTopCardTargetPlayerEffect extends OneShotEffect {

    protected int amount;
    
    public LookLibraryTopCardTargetPlayerEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        setText();
    }

    public LookLibraryTopCardTargetPlayerEffect() {
        this(1);
    }

    public LookLibraryTopCardTargetPlayerEffect(final LookLibraryTopCardTargetPlayerEffect effect) {
        super(effect);
        amount = effect.amount;
    }

    @Override
    public LookLibraryTopCardTargetPlayerEffect copy() {
        return new LookLibraryTopCardTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && targetPlayer != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(targetPlayer.getLibrary().getTopCards(game, amount));
            player.lookAtCards(sourceObject.getName(), cards, game);
            return true;
        }
        return false;
    }
    
    private void setText() {
        StringBuilder sb = new StringBuilder("look at the top ");        
        if (amount > 1) {
            sb.append(CardUtil.numberToText(amount));
            sb.append(" cards ");
        }
        else {
            sb.append(" card ");
        }
        sb.append("of target player's library");
        this.staticText = sb.toString();
    }
}
