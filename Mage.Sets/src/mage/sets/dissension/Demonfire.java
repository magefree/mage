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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author JotaPeRL
 */
public class Demonfire extends CardImpl {

    public Demonfire(UUID ownerId) {
        super(ownerId, 60, "Demonfire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "DIS";

        // Demonfire deals X damage to target creature or player.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new ManacostVariableValue()),
                new InvertCondition(HellbentCondition.getInstance()),
                "{this} deals X damage to target creature or player"));

        // If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher());

        // Hellbent - If you have no cards in hand, Demonfire can't be countered by spells or abilities and the damage can't be prevented.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new ManacostVariableValue(), false),
                HellbentCondition.getInstance(),
                "<br/><i>Hellbent</i> - If you have no cards in hand, {this} can't be countered by spells or abilities and the damage can't be prevented."));
        // can't be countered
        Effect effect = new CantBeCounteredSourceEffect();
        effect.setText("");
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new ConditionalContinuousRuleModifyingEffect(
                (CantBeCounteredSourceEffect) effect,
                HellbentCondition.getInstance())));

        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public Demonfire(final Demonfire card) {
        super(card);
    }

    @Override
    public Demonfire copy() {
        return new Demonfire(this);
    }
}
