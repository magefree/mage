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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class HullBreach extends CardImpl<HullBreach> {

    public HullBreach(UUID ownerId) {
        super(ownerId, 193, "Hull Breach", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}{G}");
        this.expansionSetCode = "C13";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Choose one - Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        // or destroy target enchantment;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        target = new TargetPermanent(new FilterEnchantmentPermanent());
        target.setRequired(true);
        mode.getTargets().add(target);
        this.getSpellAbility().addMode(mode);
        // or destroy target artifact and target enchantment.
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        target = new TargetArtifactPermanent();
        target.setRequired(true);
        mode.getTargets().add(target);
        Effect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("and target enchantment");
        mode.getEffects().add(effect);
        target = new TargetPermanent(new FilterEnchantmentPermanent());
        target.setRequired(true);
        mode.getTargets().add(target);
        this.getSpellAbility().addMode(mode);


    }

    public HullBreach(final HullBreach card) {
        super(card);
    }

    @Override
    public HullBreach copy() {
        return new HullBreach(this);
    }
}
