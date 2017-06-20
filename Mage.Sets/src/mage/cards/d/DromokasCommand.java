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
package mage.cards.d;

import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class DromokasCommand extends CardImpl {

    private static final FilterEnchantmentPermanent filterEnchantment = new FilterEnchantmentPermanent("an enchantment");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature to put a +1/+1 counter on it");
    private static final FilterCreaturePermanent filterUncontrolledCreature = new FilterCreaturePermanent("creature you don't control");

    static {
        filterUncontrolledCreature.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public DromokasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Prevent all damage target instant or sorcery spell would deal this turn;
        this.getSpellAbility().getEffects().add(new PreventDamageByTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().getTargets().add(new TargetSpell(new FilterInstantOrSorcerySpell()));

        // or Target player sacrifices an enchantment;
        Mode mode = new Mode();
        Effect effect = new SacrificeEffect(filterEnchantment, 1, "target player");
        effect.setText("Target player sacrifices an enchantment");
        effect.setApplyEffectsAfter(); // so P/T chnaging effects take place before the fighting effect is applied
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);

        // Put a +1/+1 counter on target creature;
        mode = new Mode();
        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on target creature");
        effect.setApplyEffectsAfter(); // so the counter is taken into account if the target is also used in mode 4
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().getModes().addMode(mode);

        // or Target creature you control fights target creature you don't control.
        mode = new Mode();
        effect = new FightTargetsEffect();
        effect.setText("Target creature you control fights target creature you don't control");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetControlledCreaturePermanent());
        mode.getTargets().add(new TargetCreaturePermanent(filterUncontrolledCreature));
        this.getSpellAbility().getModes().addMode(mode);

    }

    public DromokasCommand(final DromokasCommand card) {
        super(card);
    }

    @Override
    public DromokasCommand copy() {
        return new DromokasCommand(this);
    }
}
