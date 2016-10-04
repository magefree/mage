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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LoneFox
 */
public class BloodFrenzy extends CardImpl {

    public BloodFrenzy(UUID ownerId) {
        super(ownerId, 164, "Blood Frenzy", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "TMP";

        // Cast Blood Frenzy only before the combat damage step.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new BloodFrenzyCastRestriction());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Target attacking or blocking creature gets +4/+0 until end of turn. Destroy that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    public BloodFrenzy(final BloodFrenzy card) {
        super(card);
    }

    @Override
    public BloodFrenzy copy() {
        return new BloodFrenzy(this);
    }
}


class BloodFrenzyCastRestriction extends ContinuousRuleModifyingEffectImpl {

    BloodFrenzyCastRestriction() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only before the combat damage step";
    }

    BloodFrenzyCastRestriction(final BloodFrenzyCastRestriction effect) {
        super(effect);
    }

    @Override
    public BloodFrenzyCastRestriction copy() {
        return new BloodFrenzyCastRestriction(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.CAST_SPELL);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getSourceId().equals(source.getSourceId())) {
            if(game.getPhase().getType().equals(TurnPhase.COMBAT)
                // There cannot be a legal target before declare attackers,
                // so in practice it is limited to these two steps.
                && (game.getStep().getType().equals(PhaseStep.DECLARE_ATTACKERS)
                || game.getStep().getType().equals(PhaseStep.DECLARE_BLOCKERS))) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
