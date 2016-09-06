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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 * As Fall of the Hammer tries to resolve, if only one of the targets is legal,
 * Fall of the Hammer will still resolve but will have no effect: If the first
 * target creature is illegal, it can't deal damage to anything. If the second
 * target creature is illegal, it can't be dealt damage.
 *
 * The amount of damage dealt is based on the first target creature's power as Fall of the Hammer resolves.


 * @author LevelX2
 */
public class FallOfTheHammer extends CardImpl {

    public FallOfTheHammer(UUID ownerId) {
        super(ownerId, 93, "Fall of the Hammer", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "BNG";


        // Target creature you control deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new FallOfTheHammerDamageEffect());
        TargetControlledCreaturePermanent target = 
                new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("Target creature: deals damage"));
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Another creature: damage dealt to");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    public FallOfTheHammer(final FallOfTheHammer card) {
        super(card);
    }

    @Override
    public FallOfTheHammer copy() {
        return new FallOfTheHammer(this);
    }
}

class FallOfTheHammerDamageEffect extends OneShotEffect {

    public FallOfTheHammerDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature you control deals damage equal to its power to another target creature";
    }

    public FallOfTheHammerDamageEffect(final FallOfTheHammerDamageEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheHammerDamageEffect copy() {
        return new FallOfTheHammerDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ownCreature = game.getPermanent(source.getFirstTarget());
        if (ownCreature != null) {
            int damage = ownCreature.getPower().getValue();
            Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (targetCreature != null) {
                targetCreature.damage(damage, ownCreature.getId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
