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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import mage.target.common.TargetCreatureOrPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox

 */
public class KaerveksTorch extends CardImpl {

    public KaerveksTorch(UUID ownerId) {
        super(ownerId, 175, "Kaervek's Torch", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "VMA";

        // As long as Kaervek's Torch is on the stack, spells that target it cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KaerveksTorchCostIncreaseEffect()));
        // Kaervek's Torch deals X damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public KaerveksTorch(final KaerveksTorch card) {
        super(card);
    }

    @Override
    public KaerveksTorch copy() {
        return new KaerveksTorch(this);
    }
}

class KaerveksTorchCostIncreaseEffect extends CostModificationEffectImpl {

    KaerveksTorchCostIncreaseEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells that target {this} cost {2} more to cast";
    }

    KaerveksTorchCostIncreaseEffect(KaerveksTorchCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if(abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility)
        {
            for(UUID modeId: abilityToModify.getModes().getSelectedModes()) {
                abilityToModify.getModes().setActiveMode(modeId);
                for(Target target: abilityToModify.getTargets()) {
                    for(UUID id: target.getTargets()) {
                        if(id.equals(source.getSourceObject(game).getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KaerveksTorchCostIncreaseEffect copy() {
        return new KaerveksTorchCostIncreaseEffect(this);
    }
}
