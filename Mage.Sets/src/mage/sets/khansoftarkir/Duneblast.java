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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Duneblast extends CardImpl {

    public Duneblast(UUID ownerId) {
        super(ownerId, 174, "Duneblast", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{G}{B}");
        this.expansionSetCode = "KTK";


        // Choose up to one creature. Destroy the rest.
        this.getSpellAbility().addEffect(new DuneblastEffect());

    }

    public Duneblast(final Duneblast card) {
        super(card);
    }

    @Override
    public Duneblast copy() {
        return new Duneblast(this);
    }
}

class DuneblastEffect extends OneShotEffect {

    public DuneblastEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose up to one creature. Destroy the rest";
    }

    public DuneblastEffect(final DuneblastEffect effect) {
        super(effect);
    }

    @Override
    public DuneblastEffect copy() {
        return new DuneblastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCreaturePermanent(0,1,new FilterCreaturePermanent("creature to keep"), true);
            target.setRequired(true);
            Permanent creatureToKeep = null;
            if (controller.choose(outcome, target, source.getSourceId(), game)) {
                creatureToKeep = game.getPermanent(target.getFirstTarget());
            }
            for(Permanent creature: game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                if (creature != creatureToKeep) {
                    creature.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
