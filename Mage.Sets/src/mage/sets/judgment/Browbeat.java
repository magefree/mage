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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author dustinconrad
 */
public class Browbeat extends CardImpl {

    public Browbeat(UUID ownerId) {
        super(ownerId, 82, "Browbeat", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "JUD";

        // Any player may have Browbeat deal 5 damage to him or her. If no one does, target player draws three cards.
        this.getSpellAbility().addEffect(new BrowbeatDrawEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Browbeat(final Browbeat card) {
        super(card);
    }

    @Override
    public Browbeat copy() {
        return new Browbeat(this);
    }
}

class BrowbeatDrawEffect extends OneShotEffect {

    public BrowbeatDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Any player may have {source} deal 5 damage to him or her. If no one does, target player draws three cards.";
    }

    public BrowbeatDrawEffect(final BrowbeatDrawEffect effect) {
        super(effect);
    }

    @Override
    public BrowbeatDrawEffect copy() {
        return new BrowbeatDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        StackObject spell = null;
        for(StackObject object : game.getStack()){
            if(object instanceof Spell && object.getSourceId().equals(source.getSourceId())){
                spell = object;
            }
        }
        if(spell != null){
            boolean drawCards = true;
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 5 damage to you?", game)){
                    drawCards = false;
                    player.damage(5, source.getSourceId(), game, false, true);
                    game.informPlayers(player.getLogName() + " has " + spell.getLogName() + " deal 5 to him or her");
                }
            }
            if (drawCards) {
                UUID targetPlayer = getTargetPointer().getFirst(game, source);
                if (targetPlayer != null) {
                    Player player = game.getPlayer(targetPlayer);
                    player.drawCards(3, game);
                }
            }
            return drawCards;
        }
        return false;
    }

}
