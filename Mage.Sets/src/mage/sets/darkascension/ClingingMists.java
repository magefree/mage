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
package mage.sets.darkascension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author BetaSteward
 */
public class ClingingMists extends CardImpl {

    public ClingingMists(UUID ownerId) {
        super(ownerId, 109, "Clinging Mists", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "DKA";

        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllEffect(null, Duration.EndOfTurn, true));

        // Fateful hour - If you have 5 or less life, tap all attacking creatures. Those creatures don't untap during their controller's next untap step.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ClingingMistsEffect(),
                FatefulHourCondition.getInstance(), "If you have 5 or less life, tap all attacking creatures. Those creatures don't untap during their controller's next untap step."));

    }

    public ClingingMists(final ClingingMists card) {
        super(card);
    }

    @Override
    public ClingingMists copy() {
        return new ClingingMists(this);
    }
}

class ClingingMistsEffect extends OneShotEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creatures");

    public ClingingMistsEffect() {
        super(Outcome.Tap);
        staticText = "tap all attacking creatures. Those creatures don't untap during their controller's next untap step";
    }

    public ClingingMistsEffect(final ClingingMistsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            creature.tap(game);
            doNotUntapNextUntapStep.add(creature);
        }
        if (!doNotUntapNextUntapStep.isEmpty()) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
            effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public ClingingMistsEffect copy() {
        return new ClingingMistsEffect(this);
    }

}
