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
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

/**
 *
 * @author jeffwadsworth
 */
public class BarrinsSpite extends CardImpl {

    public BarrinsSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Choose two target creatures controlled by the same player. Their controller chooses and sacrifices one of them. Return the other to its owner's hand.
        this.getSpellAbility().addEffect(new BarrinsSpiteEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2, 2, new FilterCreaturePermanent(), false));

    }

    public BarrinsSpite(final BarrinsSpite card) {
        super(card);
    }

    @Override
    public BarrinsSpite copy() {
        return new BarrinsSpite(this);
    }
}

class BarrinsSpiteEffect extends OneShotEffect {

    public BarrinsSpiteEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target creatures controlled by the same player. Their controller chooses and sacrifices one of them. Return the other to its owner's hand";
    }

    public BarrinsSpiteEffect(final BarrinsSpiteEffect effect) {
        super(effect);
    }

    @Override
    public BarrinsSpiteEffect copy() {
        return new BarrinsSpiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            boolean sacrificeDone = false;
            int count = 0;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    Player controllerOfCreature = game.getPlayer(creature.getControllerId());
                    if ((count == 0
                            && controllerOfCreature.chooseUse(Outcome.Sacrifice, "Sacrifice " + creature.getLogName() + '?', source, game))
                            || (count == 1 
                            && !sacrificeDone)) {
                        creature.sacrifice(source.getId(), game);
                        sacrificeDone = true;
                    } else {
                        creature.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                    count++;
                }
            }
            return true;
        }
        return false;
    }
}
