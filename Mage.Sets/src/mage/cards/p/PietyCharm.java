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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class PietyCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Aura attached to a creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Soldier creature");

    static {
        filter1.add(new SubtypePredicate("Aura"));
        filter1.add(new AttachedToPredicate(new FilterCreaturePermanent()));
        filter2.add(new SubtypePredicate("Soldier"));
    }

    public PietyCharm(UUID ownerId) {
        super(ownerId, 49, "Piety Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ONS";

        // Choose one - Destroy target Aura attached to a creature
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or target Soldier creature gets +2/+2 until end of turn
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().addMode(mode);
        // or creatures you control gain vigilance until end of turn.
        mode = new Mode();
        mode.getEffects().add(new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures you control")));
        this.getSpellAbility().addMode(mode);
    }

    public PietyCharm(final PietyCharm card) {
        super(card);
    }

    @Override
    public PietyCharm copy() {
        return new PietyCharm(this);
    }
}
