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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public class SerpentineSpike extends CardImpl {

    public SerpentineSpike(UUID ownerId) {
        super(ownerId, 133, "Serpentine Spike", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");
        this.expansionSetCode = "BFZ";

        // Devoid
        Ability ability = new DevoidAbility(this.color);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Serpentine Spike deals 2 damage to target creature, 3 damage to another target creature, and 4 damage to a third target creature. If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new SerpentineSpikeEffect());

        TargetCreaturePermanent target = new TargetCreaturePermanent(new FilterCreaturePermanent("creature (2 damage)"));
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature (3 damage)");
        filter.add(new AnotherTargetPredicate(2));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);

        filter = new FilterCreaturePermanent("another target creature (4 damage)");
        filter.add(new AnotherTargetPredicate(3));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(3);
        this.getSpellAbility().addTarget(target);

        Effect effect = new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn);
        effect.setText("If a creature dealt damage this way would die this turn, exile it instead");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addWatcher(new DamagedByWatcher());
    }

    public SerpentineSpike(final SerpentineSpike card) {
        super(card);
    }

    @Override
    public SerpentineSpike copy() {
        return new SerpentineSpike(this);
    }
}

class SerpentineSpikeEffect extends OneShotEffect {

    public SerpentineSpikeEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature, 3 damage to another target creature, and 4 damage to a third target creature";
    }

    public SerpentineSpikeEffect(final SerpentineSpikeEffect effect) {
        super(effect);
    }

    @Override
    public SerpentineSpikeEffect copy() {
        return new SerpentineSpikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            permanent.damage(2, source.getSourceId(), game, false, true);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), game, false, true);
        }
        permanent = game.getPermanent(source.getTargets().get(2).getFirstTarget());
        if (permanent != null) {
            permanent.damage(4, source.getSourceId(), game, false, true);
        }
        return true;
    }
}
