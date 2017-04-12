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
package mage.cards.m;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class MobRule extends CardImpl {

    public MobRule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");

        // Choose one
        // Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new MobRuleEffect(ComparisonType.MORE_THAN, 3));

        // Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn.
        Mode mode = new Mode();
        mode.getEffects().add(new MobRuleEffect(ComparisonType.FEWER_THAN, 4));
        this.getSpellAbility().addMode(mode);
    }

    public MobRule(final MobRule card) {
        super(card);
    }

    @Override
    public MobRule copy() {
        return new MobRule(this);
    }
}

class MobRuleEffect extends OneShotEffect {

    ComparisonType type = null;
    int power = 0;

    public MobRuleEffect(ComparisonType type, int power) {
        super(Outcome.GainControl);
        this.type = type;
        this.power = power;
        if (type == ComparisonType.MORE_THAN) {
            this.staticText = "Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn";
        } else {
            this.staticText = "Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn";
        }
    }

    public MobRuleEffect(final MobRuleEffect effect) {
        super(effect);
        this.type = effect.type;
        this.power = effect.power;
    }

    @Override
    public MobRuleEffect copy() {
        return new MobRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(type, power));
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(filter, game);
        for (Permanent creature : creatures) {
            ContinuousEffect effect = new MobRuleControlAllEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(effect, source);
            applied = true;
        }
        for (Permanent creature : creatures) {
            creature.untap(game);
            applied = true;
        }
        for (Permanent creature : creatures) {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(effect, source);
            applied = true;
        }
        return applied;
    }
}

class MobRuleControlAllEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public MobRuleControlAllEffect(UUID controllerId) {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public MobRuleControlAllEffect(final MobRuleControlAllEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public MobRuleControlAllEffect copy() {
        return new MobRuleControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null && controllerId != null) {
            return creature.changeControllerId(controllerId, game);
        }
        return false;
    }
}
