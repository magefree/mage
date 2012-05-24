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
package mage.sets.conflux;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class DarkTemper extends CardImpl<DarkTemper> {

    public DarkTemper(UUID ownerId) {
        super(ownerId, 61, "Dark Temper", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "CON";

        this.color.setRed(true);

        // Dark Temper deals 2 damage to target creature. If you control a black permanent, destroy the creature instead.
        this.getSpellAbility().addEffect(new DarkTemperEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public DarkTemper(final DarkTemper card) {
        super(card);
    }

    @Override
    public DarkTemper copy() {
        return new DarkTemper(this);
    }
}

class DarkTemperEffect extends OneShotEffect<DarkTemperEffect> {

    public DarkTemperEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature. If you control a black permanent, destroy the creature instead";
    }

    public DarkTemperEffect(final DarkTemperEffect effect) {
        super(effect);
    }

    @Override
    public DarkTemperEffect copy() {
        return new DarkTemperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
        if (permanent == null) {
            return false;
        }

        FilterPermanent filter = new FilterPermanent("black permanent");
        filter.getColor().setBlack(true);
        filter.setUseColor(true);

        if (game.getBattlefield().countAll(filter, source.getControllerId(), game) == 0) {
            permanent.damage(2, source.getSourceId(), game, true, false);
        } else {
            permanent.destroy(source.getId(), game, false);
        }
        return true;
    }
}
