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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ForkedBolt extends CardImpl<ForkedBolt> {

    public ForkedBolt(UUID ownerId) {
        super(ownerId, 146, "Forked Bolt", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // Forked Bolt deals 2 damage divided as you choose among one or two target creatures and/or players.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(1, 2));
        this.getSpellAbility().addEffect(new ForkedBoltEffect());
    }

    public ForkedBolt(final ForkedBolt card) {
        super(card);
    }

    @Override
    public ForkedBolt copy() {
        return new ForkedBolt(this);
    }
}

class ForkedBoltEffect extends OneShotEffect<ForkedBoltEffect> {

    public ForkedBoltEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals 2 damage divided as you choose among one or two target creatures and/or players";
    }

    public ForkedBoltEffect(final ForkedBoltEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numTargets = targetPointer.getTargets(game, source).size();
        int damage = 2;
        if (numTargets > 0) {
            int damagePer = damage/numTargets;
            if (damagePer > 0) {
                for (UUID targetId: targetPointer.getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.damage(damagePer, source.getSourceId(), game, true, false);
                    }
                    else {
                        Player player = game.getPlayer(targetId);
                        if (player != null) {
                            player.damage(damagePer, source.getSourceId(), game, false, true);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ForkedBoltEffect copy() {
        return new ForkedBoltEffect(this);
    }

}
