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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Insurrection extends CardImpl<Insurrection> {

    public Insurrection(UUID ownerId) {
        super(ownerId, 213, "Insurrection", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");
        this.expansionSetCode = "ONS";

        this.color.setRed(true);

        // Untap all creatures and gain control of them until end of turn. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new InsurrectionEffect());
    }

    public Insurrection(final Insurrection card) {
        super(card);
    }

    @Override
    public Insurrection copy() {
        return new Insurrection(this);
    }
}

class InsurrectionEffect extends OneShotEffect<InsurrectionEffect> {

    public InsurrectionEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "Untap all creatures and gain control of them until end of turn. They gain haste until end of turn";
    }

    public InsurrectionEffect(final InsurrectionEffect effect) {
        super(effect);
    }

    @Override
    public InsurrectionEffect copy() {
        return new InsurrectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        ContinuousEffect gainControl = new GainControlTargetEffect(Duration.EndOfTurn);
        ContinuousEffect gainHaste = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
            creature.untap(game);
            gainControl.setTargetPointer(new FixedTarget(creature.getId()));
            gainHaste.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(gainControl, source);
            game.addEffect(gainHaste, source);
            result = true;
        }
        return result;
    }
}
