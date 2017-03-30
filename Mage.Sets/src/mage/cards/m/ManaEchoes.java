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
package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ManaEchoes extends CardImpl {

    public ManaEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // Whenever a creature enters the battlefield, you may add X mana of {C} to your mana pool, where X is the number of creatures you control that share a creature type with it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new ManaEchoesEffect(), new FilterCreaturePermanent("a creature"), true, SetTargetPointer.PERMANENT, ""));
    }

    public ManaEchoes(final ManaEchoes card) {
        super(card);
    }

    @Override
    public ManaEchoes copy() {
        return new ManaEchoes(this);
    }
}

class ManaEchoesEffect extends OneShotEffect {

    public ManaEchoesEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may add X mana of {C} to your mana pool, where X is the number of creatures you control that share a creature type with it";
    }

    public ManaEchoesEffect(final ManaEchoesEffect effect) {
        super(effect);
    }

    @Override
    public ManaEchoesEffect copy() {
        return new ManaEchoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            int foundCreatures = 0;
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                if (permanent.shareSubtypes(perm, game)) {
                    foundCreatures++;
                }
            }
            if (foundCreatures > 0) {
                controller.getManaPool().addMana(Mana.ColorlessMana(foundCreatures), game, source);
            }
            return true;
        }
        return false;
    }
}
