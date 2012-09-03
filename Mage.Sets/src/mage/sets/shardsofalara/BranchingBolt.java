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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class BranchingBolt extends CardImpl<BranchingBolt> {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent filterNotFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterNotFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BranchingBolt(UUID ownerId) {
        super(ownerId, 158, "Branching Bolt", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}{G}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Choose one or both - Branching Bolt deals 3 damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterFlying));
        // or Branching Bolt deals 3 damage to target creature without flying.
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(3));
        mode.getTargets().add(new TargetCreaturePermanent(filterNotFlying));
        this.getSpellAbility().addMode(mode);
        // both
        mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(3));
        mode.getTargets().add(new TargetCreaturePermanent(filterFlying));
        mode.getTargets().add(new TargetCreaturePermanent(filterNotFlying));
        this.getSpellAbility().addMode(mode);
    }

    public BranchingBolt(final BranchingBolt card) {
        super(card);
    }

    @Override
    public BranchingBolt copy() {
        return new BranchingBolt(this);
    }
}

class BranchingBoltEffect extends OneShotEffect<BranchingBoltEffect> {

    public BranchingBoltEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to target creature with flying and to target creature without flying";
    }

    public BranchingBoltEffect(final BranchingBoltEffect effect) {
        super(effect);
    }

    @Override
    public BranchingBoltEffect copy() {
        return new BranchingBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), game, true, false);
        }

        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), game, true, false);
        }
        return true;
    }
}
