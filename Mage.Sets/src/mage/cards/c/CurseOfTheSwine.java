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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CurseOfTheSwineBoarToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CurseOfTheSwine extends CardImpl {

    public CurseOfTheSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Exile X target creatures. For each creature exiled this way, its controller creates a 2/2 green Boar creature token.
        this.getSpellAbility().addEffect(new CurseOfTheSwineEffect());
        // Correct number of targets will be set in adjustTargets
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
        }
    }

    public CurseOfTheSwine(final CurseOfTheSwine card) {
        super(card);
    }

    @Override
    public CurseOfTheSwine copy() {
        return new CurseOfTheSwine(this);
    }
}

class CurseOfTheSwineEffect extends OneShotEffect {

    public CurseOfTheSwineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile X target creatures. For each creature exiled this way, its controller creates a 2/2 green Boar creature token";
    }

    public CurseOfTheSwineEffect(final CurseOfTheSwineEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheSwineEffect copy() {
        return new CurseOfTheSwineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent creature = game.getPermanent(targetId);
            if (creature != null) {
                if (creature.moveToExile(null, null, source.getSourceId(), game) || creature.moveToZone(Zone.COMMAND, source.getSourceId(), game, false)) {
                    CurseOfTheSwineBoarToken swineToken = new CurseOfTheSwineBoarToken();
                    swineToken.putOntoBattlefield(1, game, source.getSourceId(), creature.getControllerId());
                }
            }
        }
        return true;
    }
}
