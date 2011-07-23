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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class GoblinArsonist extends CardImpl<GoblinArsonist> {

    public GoblinArsonist(UUID ownerId) {
        super(ownerId, 147, "Goblin Arsonist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new DiesTriggeredAbility(new GoblinArsonistEffect(), true));
    }

    public GoblinArsonist(final GoblinArsonist card) {
        super(card);
    }

    @Override
    public GoblinArsonist copy() {
        return new GoblinArsonist(this);
    }
}

class GoblinArsonistEffect extends OneShotEffect<GoblinArsonistEffect> {

    public GoblinArsonistEffect() {
        super(Outcome.Damage);
        staticText = "have it deal 1 damage to target creature or player";
    }

    public GoblinArsonistEffect(final GoblinArsonistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
        Player player = game.getPlayer(source.getControllerId());
        player.choose(Outcome.Damage, target, game);

        Permanent arsonist = game.getPermanent(source.getSourceId());
        if (arsonist != null) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.damage(1, arsonist.getId(), game, true, false);
                return true;
            }

            Player targetPlayer = game.getPlayer(target.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.damage(1, arsonist.getId(), game, true, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public GoblinArsonistEffect copy() {
        return new GoblinArsonistEffect(this);
    }

}
