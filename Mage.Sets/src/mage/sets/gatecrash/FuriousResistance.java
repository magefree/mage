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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class FuriousResistance extends CardImpl<FuriousResistance> {
    
    private final static FilterCreaturePermanent filter = new FilterBlockingCreature("blocking creature");

    public FuriousResistance(UUID ownerId) {
        super(ownerId, 93, "Furious Resistance", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);

        // Target blocking creature gets +3/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new FuriousResistanceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public FuriousResistance(final FuriousResistance card) {
        super(card);
    }

    @Override
    public FuriousResistance copy() {
        return new FuriousResistance(this);
    }
}

class FuriousResistanceEffect extends OneShotEffect<FuriousResistanceEffect> {

    public FuriousResistanceEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "Target blocking creature gets +3/+0 and gains first strike until end of turn";
    }

    public FuriousResistanceEffect(final FuriousResistanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        
        ContinuousEffect effect = new BoostTargetEffect(3, 0, Constants.Duration.EndOfTurn);
        ContinuousEffect effect2 = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Constants.Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId()));
        effect2.setTargetPointer(new FixedTarget(target.getId()));
        game.addEffect(effect, source);
        game.addEffect(effect2, source);
        return true;
    }

    @Override
    public FuriousResistanceEffect copy() {
        return new FuriousResistanceEffect(this);
    }
}
