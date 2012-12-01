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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DevouringRage extends CardImpl<DevouringRage> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of Spirits");

    static {
        filter.add(new SubtypePredicate("Spirit"));
    }

    public DevouringRage(UUID ownerId) {
        super(ownerId, 164, "Devouring Rage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");
        this.color.setRed(true);

        // As an additional cost to cast Devouring Rage, you may sacrifice any number of Spirits.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true, false)));

        // Target creature gets +3/+0 until end of turn. For each Spirit sacrificed this way, that creature gets an additional +3/+0 until end of turn
        this.getSpellAbility().addEffect(new DevouringRageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));

    }

    public DevouringRage(final DevouringRage card) {
        super(card);
    }

    @Override
    public DevouringRage copy() {
        return new DevouringRage(this);
    }
}

class DevouringRageEffect extends OneShotEffect<DevouringRageEffect> {

    public DevouringRageEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target creature gets +3/+0 until end of turn. For each Spirit sacrificed this way, that creature gets an additional +3/+0 until end of turn";
    }

    public DevouringRageEffect(final DevouringRageEffect effect) {
        super(effect);
    }

    @Override
    public DevouringRageEffect copy() {
        return new DevouringRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberSpirits = 0;
        for (Cost cost :source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                numberSpirits = ((SacrificeTargetCost) cost).getPermanents().size();
            }
        }
        int amount = 3 + (numberSpirits * 3);
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            ContinuousEffect effect = new BoostTargetEffect(amount, 0, Constants.Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetCreature.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
