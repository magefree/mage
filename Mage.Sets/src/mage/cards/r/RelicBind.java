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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class RelicBind extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public RelicBind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Whenever enchanted artifact becomes tapped, choose one
        // — Relic Bind deals 1 damage to target player.
        Ability ability2 = new BecomesTappedAttachedTriggeredAbility(new DamageTargetEffect(1), "enchanted artifact");
        ability2.addTarget(new TargetPlayer());
        // — Target player gains 1 life.
        Mode mode = new Mode();
        mode.getEffects().add(new GainLifeTargetEffect(1));
        mode.getTargets().add(new TargetPlayer());
        ability2.addMode(mode);

        this.addAbility(ability2);
    }

    public RelicBind(final RelicBind card) {
        super(card);
    }

    @Override
    public RelicBind copy() {
        return new RelicBind(this);
    }
}
