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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 *
 * @author MarcoMarin
 */
public class YdwenEfreet extends CardImpl {

    public YdwenEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add("Efreet");
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Whenever Ydwen Efreet blocks, flip a coin. If you lose the flip, remove Ydwen Efreet from combat and it can't block this turn. Creatures it was blocking that had become blocked by only Ydwen Efreet this combat become unblocked.
        this.addAbility(new BlocksTriggeredAbility(new YdwenEfreetEffect(), false));
    }

    public YdwenEfreet(final YdwenEfreet card) {
        super(card);
    }

    @Override
    public YdwenEfreet copy() {
        return new YdwenEfreet(this);
    }
}

class YdwenEfreetEffect extends OneShotEffect {

    public YdwenEfreetEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, remove {this} from combat and it can't block.";
    }

    public YdwenEfreetEffect(YdwenEfreetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getSourceId());
        if (controller != null && creature != null) {
            if (controller.flipCoin(game)) {
                return true;
            } else {
                creature.removeFromCombat(game);
                creature.setMaxBlocks(0);
                return true;
                }
            }
        return false;
    }

    @Override
    public YdwenEfreetEffect copy() {
        return new YdwenEfreetEffect(this);
    }
}