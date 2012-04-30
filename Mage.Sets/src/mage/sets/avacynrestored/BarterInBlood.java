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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class BarterInBlood extends CardImpl<BarterInBlood> {

    public BarterInBlood(UUID ownerId) {
        super(ownerId, 85, "Barter in Blood", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // Each player sacrifices two creatures.
        this.getSpellAbility().addEffect(new BarterInBloodEffect());
    }

    public BarterInBlood(final BarterInBlood card) {
        super(card);
    }

    @Override
    public BarterInBlood copy() {
        return new BarterInBlood(this);
    }
}

class BarterInBloodEffect extends OneShotEffect<BarterInBloodEffect> {

    public BarterInBloodEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each player sacrifices two creatures";
    }

    public BarterInBloodEffect(final BarterInBloodEffect effect) {
        super(effect);
    }

    @Override
    public BarterInBloodEffect copy() {
        return new BarterInBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = Math.min(2, game.getBattlefield().countAll(filter, player.getId()));
                    Target target = new TargetControlledPermanent(amount, amount, filter, false);
                    target.setRequired(true);
                    if (amount > 0 && target.canChoose(player.getId(), game)
                            && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(targetId);
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
