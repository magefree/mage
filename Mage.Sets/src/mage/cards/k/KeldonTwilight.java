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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author spjspj & L_J
 */
public class KeldonTwilight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you controlled since the beginning of the turn");

    static {
        filter.add(new ControlledFromStartOfControllerTurnPredicate());
    }

    public KeldonTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // At the beginning of each player's end step, if no creatures attacked this turn, that player sacrifices a creature he or she controlled since the beginning of the turn.
        Effect effect = new SacrificeEffect(filter, 1, "that player ");
        effect.setText("that player sacrifices a creature he or she controlled since the beginning of the turn");
        BeginningOfEndStepTriggeredAbility ability
                = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, new KeldonTwilightCondition(), false);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    public KeldonTwilight(final KeldonTwilight card) {
        super(card);
    }

    @Override
    public KeldonTwilight copy() {
        return new KeldonTwilight(this);
    }
}

class KeldonTwilightCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.getAttackedThisTurnCreatures().isEmpty();
        }
        return true;
    }

    @Override
    public String toString() {
        return "if no creatures attacked this turn";
    }

}
