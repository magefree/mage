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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author noxx

 */
public class VexingDevil extends CardImpl<VexingDevil> {

    public VexingDevil(UUID ownerId) {
        super(ownerId, 164, "Vexing Devil", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Devil");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Vexing Devil enters the battlefield, any opponent may have it deal 4 damage to him or her. If a player does, sacrifice Vexing Devil.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VexingDevilEffect(), false));
    }

    public VexingDevil(final VexingDevil card) {
        super(card);
    }

    @Override
    public VexingDevil copy() {
        return new VexingDevil(this);
    }
}

class VexingDevilEffect extends OneShotEffect<VexingDevilEffect> {

    public VexingDevilEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "any opponent may have it deal 4 damage to him or her. If a player does, sacrifice Vexing Devil";
    }

    VexingDevilEffect(final VexingDevilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Make ").append(permanent.getName()).append(" deal 4 damage to you?");

            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for (UUID opponentUuid : opponents) {
                Player opponent = game.getPlayer(opponentUuid);
                if (opponent != null && opponent.chooseUse(Constants.Outcome.LoseLife, sb.toString(), game)) {
                    game.informPlayers(opponent.getName() + " has chosen to receive 4 damage from " + permanent.getName());
                    int dealt = opponent.damage(4, permanent.getId(), game, false, true);
                    if (dealt == 4) {
                        game.informPlayers(opponent.getName() + " was dealt 4 damage so " + permanent.getName() + " will be sacrificed.");
                        permanent.sacrifice(source.getId(), game);
                        return true;
                    } else {
                        game.informPlayers("4 damage wasn't dealt so " + permanent.getName() + " won't be sacrificed.");
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public VexingDevilEffect copy() {
        return new VexingDevilEffect(this);
    }

}
