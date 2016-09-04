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
package mage.sets.chronicles;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public class LivingArmor extends CardImpl {

    public LivingArmor(UUID ownerId) {
        super(ownerId, 83, "Living Armor", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "CHR";

        // {tap}, Sacrifice Living Armor: Put X +0/+1 counters on target creature, where X is that creature's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LivingArmorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public LivingArmor(final LivingArmor card) {
        super(card);
    }

    @Override
    public LivingArmor copy() {
        return new LivingArmor(this);
    }

    class LivingArmorEffect extends OneShotEffect {

        public LivingArmorEffect() {
            super(Outcome.BoostCreature);
            this.staticText = "Put X +0/+1 counters on target creature, where X is that creature's converted mana cost";
        }

        public LivingArmorEffect(final LivingArmorEffect effect) {
            super(effect);
        }

        @Override
        public LivingArmorEffect copy() {
            return new LivingArmorEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature != null) {
                int amount = creature.getConvertedManaCost();
                creature.addCounters(new BoostCounter(0, 1, amount), game);
            }
            return false;
        }
    }
}
