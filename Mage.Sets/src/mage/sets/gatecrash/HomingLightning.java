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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class HomingLightning extends CardImpl<HomingLightning> {

    public HomingLightning(UUID ownerId) {
        super(ownerId, 96, "Homing Lightning", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);

        // Homing Lightning deals 4 damage to target creature and each other creature with the same name as that creature.
        this.getSpellAbility().addEffect(new HomingLightningEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public HomingLightning(final HomingLightning card) {
        super(card);
    }

    @Override
    public HomingLightning copy() {
        return new HomingLightning(this);
    }
}

class HomingLightningEffect extends OneShotEffect<HomingLightningEffect> {

    public HomingLightningEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals 4 damage to target creature and each other creature with the same name as that creature";
    }

    public HomingLightningEffect(final HomingLightningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new NamePredicate(target.getName()));
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        if (target != null) {
            for (Permanent creature : creatures) {
                if (creature != null) {
                    creature.damage(4, id, game, true, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HomingLightningEffect copy() {
        return new HomingLightningEffect(this);
    }
}