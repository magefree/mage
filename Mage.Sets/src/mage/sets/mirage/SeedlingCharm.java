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
package mage.sets.mirage;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class SeedlingCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Aura attached to a creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("green creature");

    static {
        filter1.add(new SubtypePredicate("Aura"));
        filter1.add(new AttachedToPredicate(new FilterCreaturePermanent()));
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SeedlingCharm(UUID ownerId) {
        super(ownerId, 138, "Seedling Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "MIR";

        // Choose one - Return target Aura attached to a creature to its owner's hand
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or regenerate target green creature
        Mode mode = new Mode();
        mode.getEffects().add(new RegenerateTargetEffect());
        mode.getTargets().add(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
        // or target creature gains trample until end of turn.
        mode = new Mode();
        mode.getEffects().add(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    public SeedlingCharm(final SeedlingCharm card) {
        super(card);
    }

    @Override
    public SeedlingCharm copy() {
        return new SeedlingCharm(this);
    }
}
